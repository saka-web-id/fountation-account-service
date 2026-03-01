package id.web.saka.fountation.account;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanService;
import id.web.saka.fountation.account.user.registration.AccountUserRegistrationService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class AccountGrpcService extends AccountServiceGrpc.AccountServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AccountGrpcService.class);

    private final AccountMembershipPlanService accountMembershipPlanService;
    private final AccountUserRegistrationService accountUserRegistrationService;
    private final AccountGrpcMapper mapper;

    public AccountGrpcService(AccountMembershipPlanService accountMembershipPlanService, 
                              AccountUserRegistrationService accountUserRegistrationService,
                              AccountGrpcMapper mapper) {
        this.accountMembershipPlanService = accountMembershipPlanService;
        this.accountUserRegistrationService = accountUserRegistrationService;
        this.mapper = mapper;
    }

    @Override
    public void getAccountMembershipPlanDetailByUserId(AccountMembershipPlanRequest request, 
                                                       StreamObserver<AccountMembershipPlanResponse> responseObserver) {
        log.info("Received gRPC request for account membership plan detail: companyId={}, userId={}, valueUserId={}",
                request.getCompanyId(), request.getUserId(), request.getValueUserId());

        accountMembershipPlanService.getAccountMembershipPlanDetailByUserId(
                request.getCompanyId(), request.getUserId(), request.getValueUserId())
                .map(mapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error fetching account membership plan detail via gRPC", error);
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during account membership plan retrieval: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

    @Override
    public void registerUser(UserRegistrationRequest request, StreamObserver<UserRegistrationResponse> responseObserver) {
        log.info("Received gRPC request for user registration: email={}", request.getUser().getEmail());

        accountUserRegistrationService.assignAccountToNewUser(mapper.toDTO(request))
                .map(mapper::toProto)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error during user registration via gRPC", error);
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during user registration: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }
}
