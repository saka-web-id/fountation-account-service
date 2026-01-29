package id.web.saka.fountation.membership;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MembershipRepository extends ReactiveCrudRepository<Membership, Long> {
    Mono<Membership> findByAccountId(Long accountId);
}
