package id.web.saka.fountation.account.user;

import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.account.user.registration.AccountUserRegistrationDTO;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.UserDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountUserService {

    private final AccountUserRepository accountUserRepository;

    public AccountUserService(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    public Mono<AccountUser> getAccountByUserId(Long userId) {
        return accountUserRepository.findByUserId(userId);
    }

    public Mono<AccountUser> assignAccountToNewUser(UserDTO user, Account createdAccount) {

        return accountUserRepository.save(new AccountUser(createdAccount.getId(), user.id() ));
    }
}
