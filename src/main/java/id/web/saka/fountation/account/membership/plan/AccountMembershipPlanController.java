package id.web.saka.fountation.account.membership.plan;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        log.info("Fetching AccountMembershipPlanDTO for valueUserId: " + valueUserId + " in companyId: " + companyId);

        return accountMembershipPlanService. getAccountMembershipPlanDetailByUserId(companyId, userId, valueUserId);
    }


}
