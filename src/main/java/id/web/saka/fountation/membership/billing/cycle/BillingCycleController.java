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
        log.info("[getBillingCycleList] Fetching billing cycle list for company ID: {} requested by user ID: {} in company ID: {}", valueCompanyId, userId, companyId);

        return billingCycleService.getBillingCycleList(companyId, userId, valueCompanyId);
    }

    @GetMapping(value = "/account/membership/billing/cycle/detail/companyId/{companyId}/userId/{userId}/valueBillingIdId/{valueCompanyId}")
    public Mono<BillingCycleDTO> getBillingCycleDetail(@PathVariable Long companyId, @PathVariable Long userId, @PathVariable Long valueBillingIdId) {
        log.info("[getBillingCycleDetail] Fetching billing cycle detail for billing ID: {} requested by user ID: {} in company ID: {}", valueBillingIdId, userId, companyId);

        return billingCycleService.getBillingCycleDetail(companyId, userId, valueBillingIdId);
    }
    @PostMapping("/account/membership/billing/cycle/add/companyId/{companyId}/userId/{userId}")
    public Mono<BillingCycleDTO> postAddBillingCycle(@RequestBody Mono<BillingCycleDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(billingCycle -> {
            log.info("[postAddBillingCycle] Adding new billing cycle for company ID: {} initiated by user ID: {} in company ID: {}", billingCycle.companyId(), userId, companyId);
            return billingCycleService.saveBillingCycle(companyId, userId, billingCycle);
        });
    }
    @PostMapping("/account/membership/billing/cycle/update/companyId/{companyId}/userId/{userId}")
    public Mono<BillingCycleDTO> postUpdateBillingCycle(@RequestBody Mono<BillingCycleDTO> payload, @PathVariable Long companyId, @PathVariable Long userId) {
        return payload.flatMap(billingCycle -> {
            log.info("[postUpdateBillingCycle] Updating billing cycle ID: {} initiated by user ID: {} in company ID: {}", billingCycle.id(), userId, companyId);

            return billingCycleService.saveBillingCycle(companyId, userId, billingCycle);
        });
    }

}
