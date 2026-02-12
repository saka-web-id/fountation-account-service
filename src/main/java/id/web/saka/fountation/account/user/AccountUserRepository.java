package id.web.saka.fountation.account.user;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountUserRepository extends org.springframework.data.repository.reactive.ReactiveCrudRepository<AccountUser, Long> {
    Mono<AccountUser> findByUserId(Long userId);
}
