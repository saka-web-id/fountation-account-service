package id.web.saka.fountation.account.membership.plan;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class AccountMembershipPlanController {

    Logger log = org.slf4j.LoggerFactory.getLogger(AccountMembershipPlanController.class);

    private final AccountMembershipPlanService accountMembershipPlanService;

    public AccountMembershipPlanController(AccountMembershipPlanService accountMembershipPlanService) {
        this.accountMembershipPlanService = accountMembershipPlanService;
    }


    @GetMapping(value = "/account/user/membership/plan/detail/companyId/{companyId}/userId/{userId}/valueUserId/{valueUserId}")
    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueUserId) {
        log.info("[getAccountMembershipPlanDetailByUserId] Fetching account membership plan detail for target user ID: {} requested by user ID: {} in company ID: {}", valueUserId, userId, companyId);

        return accountMembershipPlanService. getAccountMembershipPlanDetailByUserId(companyId, userId, valueUserId);
    }

    /*@GetMapping(value = "/account/user/membership/plan/list/companyId/{companyId}/userId/{userId}/valueCompanyId/{valueCompanyId}")
    public reactor.core.publisher.Flux<id.web.saka.fountation.membership.plan.MembershipPlanDTO> getMembershipPlanListByCompanyId(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueCompanyId) {
        log.info("[Account-Service] Fetching membership plan list for company ID: {} requested by user ID: {}", valueCompanyId, userId);

        return accountMembershipPlanService.getMembershipPlanListByCompanyId(companyId, userId, valueCompanyId);
    }

    @PostMapping(value = "/account/user/membership/plan/update/companyId/{companyId}/userId/{userId}/valueUserId/{valueUserId}")
    public Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(
            @PathVariable Long companyId,
            @PathVariable Long userId,
            @PathVariable Long valueUserId,
            @RequestBody UpdateAccountMembershipPlanRequest request) {
        log.info("[Account-Service] Updating account membership plan for target user ID: {} requested by user ID: {} in company ID: {}", valueUserId, userId, companyId);

        return accountMembershipPlanService.updateAccountMembershipPlan(
                companyId,
                userId,
                valueUserId,
                request.accountStatus(),
                request.membershipStatus(),
                request.membershipPlanId()
        );
    }

    public record UpdateAccountMembershipPlanRequest(
            id.web.saka.fountation.account.Account.AccountStatus accountStatus,
            id.web.saka.fountation.membership.Membership.MembershipStatus membershipStatus,
            Long membershipPlanId
    ) {}*/


}
