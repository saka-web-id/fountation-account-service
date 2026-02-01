package id.web.saka.fountation.membership.billing.cycle;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BillingCycleService {

    private final BillingCycleRepository billingCycleRepository;

    private final BillingCycleMapper billingCycleMapper;

    public BillingCycleService(BillingCycleRepository billingCycleRepository, BillingCycleMapper billingCycleMapper) {
        this.billingCycleRepository = billingCycleRepository;
        this.billingCycleMapper = billingCycleMapper;
    }

    public Flux<BillingCycleDTO> getBillingCycleList(Long companyId, Long userId, Long valueCompanyId) {

        return billingCycleRepository.findByCompanyId(companyId)
                .map(billingCycleMapper::toDto);

    }

    public Mono<BillingCycleDTO> getBillingCycleDetail(Long companyId, Long userId, Long valueBillingIdId) {

        return billingCycleRepository.findById(valueBillingIdId)
                .map(billingCycleMapper::toDto);
    }

    public Mono<BillingCycleDTO> saveBillingCycle(Long companyId, Long userId, BillingCycleDTO billingCycle) {

        return Mono.just(billingCycle)
                .map(billingCycleMapper::toEntity)
                .flatMap(billingCycleRepository::save)
                .map(billingCycleMapper::toDto);

    }

}
