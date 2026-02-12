package id.web.saka.fountation.account;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }
}
