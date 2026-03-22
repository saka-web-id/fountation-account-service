package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MembershipPlanGrpcService extends MembershipPlanServiceGrpc.MembershipPlanServiceImplBase {

    Logger log = LoggerFactory.getLogger(MembershipPlanGrpcService.class);

    private final MembershipPlanGrpcMapper membershipPlanGrpcMapper;

    private final AccountMembershipPlanService accountMembershipPlanService;

    public MembershipPlanGrpcService(MembershipPlanGrpcMapper membershipPlanGrpcMapper, AccountMembershipPlanService accountMembershipPlanService) {
        this.membershipPlanGrpcMapper = membershipPlanGrpcMapper;
        this.accountMembershipPlanService = accountMembershipPlanService;
    }

    @Override
    public void getMembershipPlanListByCompanyId(MembershipPlanListRequest request, StreamObserver<MembershipPlanListResponse> responseObserver) {
        log.info("Received gRPC request for membership plan list: valueCompanyId={}", request.getValueCompanyId());

        accountMembershipPlanService.getMembershipPlanListByCompanyId(request.getCompanyId(), request.getUserId(), request.getValueCompanyId())
                .map(membershipPlanGrpcMapper::toProto)
                .collectList()
                .map(plans -> MembershipPlanListResponse.newBuilder()
                        .addAllMembershipPlans(plans)
                        .build())
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error fetching membership plan list via gRPC", error);
                            responseObserver.onError(io.grpc.Status.INTERNAL
                                    .withDescription("Error during membership plan list retrieval: " + error.getMessage())
                                    .asRuntimeException());
                        }
                );
    }

}
