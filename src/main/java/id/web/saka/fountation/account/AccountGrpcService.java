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
    private final id.web.saka.fountation.membership.plan.MembershipPlanGrpcMapper membershipPlanGrpcMapper;

    public AccountGrpcService(AccountMembershipPlanService accountMembershipPlanService, 
                              AccountUserRegistrationService accountUserRegistrationService,
                              AccountGrpcMapper mapper,
                              id.web.saka.fountation.membership.plan.MembershipPlanGrpcMapper membershipPlanGrpcMapper) {
        this.accountMembershipPlanService = accountMembershipPlanService;
        this.accountUserRegistrationService = accountUserRegistrationService;
        this.mapper = mapper;
        this.membershipPlanGrpcMapper = membershipPlanGrpcMapper;
    }
}
