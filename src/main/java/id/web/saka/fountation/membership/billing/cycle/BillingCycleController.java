package id.web.saka.fountation.membership.billing.cycle;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class BillingCycleController {

    Logger log = org.slf4j.LoggerFactory.getLogger(BillingCycleController.class);

    private final BillingCycleService billingCycleService;

    public BillingCycleController(BillingCycleService billingCycleService) {
        this.billingCycleService = billingCycleService;
    }

    @GetMapping(value = "/account/membership/billing/cycle/list/companyId/{companyId}/userId/{userId}/valueCompanyId/{valueCompanyId}")
    public Flux<BillingCycleDTO> getBillingCycleList(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueCompanyId) {
        log.info("Fetching getBillingCycleList userId: " + userId + " in companyId: " + companyId + " for valueCompanyId: " + valueCompanyId);

        return billingCycleService.getBillingCycleList(companyId, userId, valueCompanyId);
    }

    @GetMapping(value = "/account/membership/billing/cycle/detail/companyId/{companyId}/userId/{userId}/valueBillingIdId/{valueCompanyId}")
    public Mono<BillingCycleDTO> getBillingCycleDetail(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueBillingIdId) {
        log.info("Fetching getBillingCycleList userId: " + userId + " in companyId: " + companyId + " for valueBillingIdId: " + valueBillingIdId);

        return billingCycleService.getBillingCycleDetail(companyId, userId, valueBillingIdId);
    }
    @PostMapping("/account/membership/billing/cycle/add/companyId/{companyId}/userId/{userId}")
    public Mono<BillingCycleDTO> postAddBillingCycle(@RequestBody Mono<BillingCycleDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(billingCycle -> {
            log.info("Adding BillingCycle: {} by userId: {} in companyId: {}", billingCycle, userId, companyId);
            return billingCycleService.saveBillingCycle(companyId, userId, billingCycle);
        });
    }
    @PostMapping("/account/membership/billing/cycle/update/companyId/{companyId}/userId/{userId}")
    public Mono<BillingCycleDTO> postUpdateBillingCycle(@RequestBody Mono<BillingCycleDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(billingCycle -> {
            log.info("Updating BillingCycle: {} by userId: {} in companyId: {}", billingCycle, userId, companyId);

            return billingCycleService.saveBillingCycle(companyId, userId, billingCycle);
        });
    }

}
