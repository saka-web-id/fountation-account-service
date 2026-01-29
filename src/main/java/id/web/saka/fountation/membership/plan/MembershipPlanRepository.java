package id.web.saka.fountation.membership.plan;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MembershipPlanRepository extends ReactiveCrudRepository<MembershipPlan, Long> {
    Flux<Object> findByCompanyId(Long valueCompanyId);
}
