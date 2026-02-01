package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.membership.AccountMembershipService;
import id.web.saka.fountation.membership.plan.MembershipPlanService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountMembershipPlanService {

    Logger log = org.slf4j.LoggerFactory.getLogger(AccountMembershipPlanService.class);

    private final AccountMembershipService accountMembershipService;

    private final MembershipPlanService membershipPlanService;

    private final AccountMembershipPlanMapper accountMembershipPlanMapper;

    public AccountMembershipPlanService(AccountMembershipService accountMembershipService, MembershipPlanService membershipPlanService, AccountMembershipPlanMapper accountMembershipPlanMapper) {
        this.accountMembershipService = accountMembershipService;
        this.membershipPlanService = membershipPlanService;
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
    }

    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(
            Long companyId, Long userId, Long valueUserId) {

        return accountMembershipService.getAccountMembershipDetailByUserId(valueUserId)
                .flatMap(accountMembershipDTO ->
                        membershipPlanService.getMembershipPlanByMembershipPlanId(accountMembershipDTO.membershipPlanId())
                                .map(membershipPlanDTO ->
                                        accountMembershipPlanMapper.toDto(accountMembershipDTO, membershipPlanDTO)
                                )
                );
    }

}
