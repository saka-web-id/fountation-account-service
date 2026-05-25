package id.web.saka.fountation.membership.plan;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class MembershipPlanController {

    Logger log = org.slf4j.LoggerFactory.getLogger(MembershipPlanController.class);

    private final MembershipPlanService membershipPlanService;

    public MembershipPlanController(MembershipPlanService membershipPlanService) {
        this.membershipPlanService = membershipPlanService;
    }

    @GetMapping(value = "/account/membership/plan/list/companyId/{companyId}/userId/{userId}/valueCompanyId/{valueCompanyId}")
    public Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueCompanyId) {
        log.info("[getMembershipPlanListByCompanyId] Fetching membership plan list for company ID: {} requested by user ID: {} in company ID: {}", valueCompanyId, userId, companyId);

        return membershipPlanService.getMembershipPlanListByCompanyId(companyId, userId, valueCompanyId);
    }

    @GetMapping(value = "/account/membership/plan/detail/companyId/{companyId}/userId/{userId}/valueMembershipPlanId/{valueMembershipPlanId}")
    public Mono<MembershipPlanDTO> getMembershipPlanDetailById(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueMembershipPlanId) {
        log.info("[getMembershipPlanDetailById] Fetching membership plan detail for plan ID: {} requested by user ID: {} in company ID: {}", valueMembershipPlanId, userId, companyId);

        return membershipPlanService.getMembershipPlanByMembershipPlanId(valueMembershipPlanId);
    }

    @PostMapping("/account/membership/plan/add/companyId/{companyId}/userId/{userId}")
    public Mono<MembershipPlanDTO> postAddMembershipPlan(@RequestBody Mono<MembershipPlanDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(membershipPlan -> {
            log.info("[postAddMembershipPlan] Adding new membership plan for company ID: {} initiated by user ID: {} in company ID: {}", membershipPlan.companyId(), userId, companyId);

            return membershipPlanService.saveMembershipPlan(companyId, userId, membershipPlan);
        });
    }

    @PostMapping("/account/membership/plan/update/companyId/{companyId}/userId/{userId}")
    public Mono<MembershipPlanDTO> postUpdateMembershipPlan(@RequestBody Mono<MembershipPlanDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(membershipPlan -> {
            log.info("[postUpdateMembershipPlan] Updating membership plan ID: {} initiated by user ID: {} in company ID: {}", membershipPlan.id(), userId, companyId);

            return membershipPlanService.saveMembershipPlan(companyId, userId, membershipPlan);
        });
    }

}
