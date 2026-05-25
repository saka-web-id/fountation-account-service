package id.web.saka.fountation.account.user;

import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.user.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountUserService {

    private static final Logger log = LoggerFactory.getLogger(AccountUserService.class);

    private final AccountUserRepository accountUserRepository;

    public AccountUserService(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    public Mono<AccountUser> getAccountByUserId(Long userId) {
        log.info("[getAccountByUserId] Retrieving account mapping for user ID: {}", userId);
        return accountUserRepository.findByUserId(userId);
    }

    public Mono<AccountUser> assignAccountToNewUser(UserDTO user, Account createdAccount) {
        log.info("[assignAccountToNewUser] Assigning account ID: {} to user ID: {}", createdAccount.getId(), user.id());

        return accountUserRepository.save(new AccountUser(createdAccount.getId(), user.id() ))
                .doOnNext(saved -> log.info("[assignAccountToNewUser] Successfully mapped user ID: {} to account ID: {}", saved.getUserId(), saved.getAccountId()));
    }
}
