package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.AccountGrpcMapper;
import id.web.saka.fountation.membership.MembershipMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class AccountMembershipPlanGrpcService extends AccountMembershipPlanServiceGrpc.AccountMembershipPlanServiceImplBase {

    Logger log = LoggerFactory.getLogger(AccountMembershipPlanGrpcService.class);

    private AccountMembershipPlanService accountMembershipPlanService;

    private AccountMembershipPlanMapper accountMembershipPlanMapper;

    private AccountGrpcMapper accountMapper;

    private MembershipMapper membershipMapper;

    public AccountMembershipPlanGrpcService(
            AccountMembershipPlanService accountMembershipPlanService,
            AccountMembershipPlanMapper accountMembershipPlanMapper,
            AccountGrpcMapper accountMapper,
            MembershipMapper membershipMapper) {
        this.accountMembershipPlanService = accountMembershipPlanService;
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
        this.accountMapper = accountMapper;
        this.membershipMapper = membershipMapper;
    }


    @Override
    public void getAccountMembershipPlanDetailByUserId(AccountMembershipPlanRequest request,
                                                       StreamObserver<AccountMembershipPlanResponse> responseObserver) {
        log.info("[getAccountMembershipPlanDetailByUserId] Received gRPC request to fetch account membership plan detail for target user ID: {} requested by user ID: {} in company ID: {}",
                request.getValueUserId(), request.getUserId(), request.getCompanyId());

        accountMembershipPlanService.getAccountMembershipPlanDetailByUserId(
                        request.getCompanyId(), request.getUserId(), request.getValueUserId())
                .map(accountMembershipPlanMapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("[getAccountMembershipPlanDetailByUserId] Internal error while fetching account membership plan detail via gRPC for target user ID: {}. Error: {}", request.getValueUserId(), error.getMessage());
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during account membership plan retrieval: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

    @Override
    public void updateAccountMembershipPlan(UpdateAccountMembershipPlanRequest request, StreamObserver<AccountMembershipPlanResponse> responseObserver) {
        log.info("[updateAccountMembershipPlan] Received gRPC request to update account membership plan for target user ID: {} requested by user ID: {} in company ID: {}", request.getValueUserId(), request.getUserId(), request.getCompanyId());

        accountMembershipPlanService.updateAccountMembershipPlan(
                        request.getCompanyId(),
                        request.getUserId(),
                        request.getValueUserId(),
                        accountMapper.toAccountStatusEntity(request.getAccountStatus()),
                        membershipMapper.toMembershipStatusEntity(request.getMembershipStatus()),
                        request.getMembershipPlanId()
                ).map(accountMembershipPlanMapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("[updateAccountMembershipPlan] Internal error while updating account membership plan via gRPC for target user ID: {}. Error: {}", request.getValueUserId(), error.getMessage());
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during account membership plan update: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

}
