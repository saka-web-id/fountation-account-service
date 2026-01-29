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
        log.info("Fetching getMembershipList userId: " + userId + " in companyId: " + companyId + " for valueCompanyId: " + valueCompanyId);

        return membershipPlanService.getMembershipPlanListByCompanyId(companyId, userId, valueCompanyId);
    }

    @PostMapping("/account/membership/plan/add/companyId/{companyId}/userId/{userId}")
    public Mono<MembershipPlanDTO> postAddMembershipPlan(@RequestBody Mono<MembershipPlanDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(membershipPlan -> {
            log.info("Adding MembershipPlan: {} by userId: {} in companyId: {}", membershipPlan, userId, companyId);

            return membershipPlanService.saveMembershipPlan(companyId, userId, membershipPlan);
        });
    }

    @PostMapping("/account/membership/plan/update/companyId/{companyId}/userId/{userId}")
    public Mono<MembershipPlanDTO> postUpdateMembershipPlan(@RequestBody Mono<MembershipPlanDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(membershipPlan -> {
            log.info("Updating MembershipPlan: {} by userId: {} in companyId: {}", membershipPlan, userId, companyId);

            return membershipPlanService.saveMembershipPlan(companyId, userId, membershipPlan);
        });
    }

}
