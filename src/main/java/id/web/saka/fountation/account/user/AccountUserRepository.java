package id.web.saka.fountation.account.user;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountUserRepository extends org.springframework.data.repository.reactive.ReactiveCrudRepository<AccountUser, Long> {
}
