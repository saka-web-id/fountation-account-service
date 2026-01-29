package id.web.saka.fountation.account;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> getAccountByUserId(Long userId) {
        return this.accountRepository.findByUserId(userId);
    }

}
