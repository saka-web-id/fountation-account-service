package id.web.saka.fountation.membership.billing.cycle;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BillingCycleRepository extends ReactiveCrudRepository<BillingCycle, Long> {

    Flux<BillingCycle> findByCompanyId(Long companyId);
}
