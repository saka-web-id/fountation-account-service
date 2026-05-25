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
        return payload
                .doOnNext(dto -> log.info("[assignAccountToNewUser] Incoming account assignment request for new user with email: {}", dto.user().email()))
                .flatMap(userRegistrationService::assignAccountToNewUser)
                .doOnSuccess(dto -> log.info("[assignAccountToNewUser] Successfully assigned account to new user with email: {}", dto.user().email()))
                .doOnError(error -> log.error("[assignAccountToNewUser] Failed to assign account to new user. Error: {}", error.getMessage()));
    }

}
