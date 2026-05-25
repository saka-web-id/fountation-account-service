package id.web.saka.fountation.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> getAccountById(Long accountId) {
        log.info("[getAccountById] Retrieving account detail for account ID: {}", accountId);
        return accountRepository.findById(accountId);
    }

    public Mono<Account> createAccount(Account account) {
        log.info("[createAccount] Creating/Updating account with number: {}", account.getNumber());
        return accountRepository.save(account)
                .doOnNext(saved -> log.info("[createAccount] Successfully saved account ID: {}", saved.getId()));
    }
}
