package id.web.saka.fountation.membership.billing.cycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BillingCycleService {

    private static final Logger log = LoggerFactory.getLogger(BillingCycleService.class);

    private final BillingCycleRepository billingCycleRepository;

    private final BillingCycleMapper billingCycleMapper;

    public BillingCycleService(BillingCycleRepository billingCycleRepository, BillingCycleMapper billingCycleMapper) {
        this.billingCycleRepository = billingCycleRepository;
        this.billingCycleMapper = billingCycleMapper;
    }

    public Flux<BillingCycleDTO> getBillingCycleList(Long companyId, Long userId, Long valueCompanyId) {
        log.info("[getBillingCycleList] Retrieving billing cycle list for company ID: {} requested by user ID: {}", valueCompanyId, userId);

        return billingCycleRepository.findByCompanyId(valueCompanyId)
                .map(billingCycleMapper::toDto);

    }

    public Mono<BillingCycleDTO> getBillingCycleDetail(Long companyId, Long userId, Long valueBillingIdId) {
        log.info("[getBillingCycleDetail] Retrieving billing cycle detail for billing ID: {} requested by user ID: {}", valueBillingIdId, userId);

        return billingCycleRepository.findById(valueBillingIdId)
                .map(billingCycleMapper::toDto);
    }

    public Mono<BillingCycleDTO> saveBillingCycle(Long companyId, Long userId, BillingCycleDTO billingCycle) {
        log.info("[saveBillingCycle] Saving billing cycle for company ID: {} initiated by user ID: {}", billingCycle.companyId(), userId);

        return Mono.just(billingCycle)
                .map(billingCycleMapper::toEntity)
                .flatMap(billingCycleRepository::save)
                .doOnNext(saved -> log.info("[saveBillingCycle] Successfully saved billing cycle ID: {} for company ID: {}", saved.getId(), saved.getCompanyId()))
                .map(billingCycleMapper::toDto);

    }

}
