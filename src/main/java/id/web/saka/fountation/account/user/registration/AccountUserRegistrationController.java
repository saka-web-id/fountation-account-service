package id.web.saka.fountation.account.user.registration;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class AccountUserRegistrationController {

    Logger log = org.slf4j.LoggerFactory.getLogger(AccountUserRegistrationController.class);
    private final AccountUserRegistrationService userRegistrationService;

    public AccountUserRegistrationController(AccountUserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/account/user/registration")
    public Mono<AccountUserRegistrationDTO> assignAccountToNewUser(@RequestBody Mono<AccountUserRegistrationDTO> payload) {
        log.info("Registering UserRole for new user: {}", payload.toString());

        return payload.
                doOnNext(dto -> log.info("Incoming assignRoleToNewUser payload: {}", dto))
                .flatMap(userRegistrationService::assignAccountToNewUser
                ).doOnError(error -> log.error("Error assigning role to new user: " + error.getMessage()));
    }

}
