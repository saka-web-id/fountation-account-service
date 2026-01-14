package id.web.saka.fountation.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;

@RestController
@RequestMapping("/api/v0")
public class AccountController {

    Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts")
    public Account generateRandom() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println(authorities);

        //return new Account( "" + new Random().nextInt(100), "test");
        return null;
    }

    /*"/api/v0/account/membership/detail/companyId/" + companyId + "/userId/" + userId)*/
    @GetMapping(value = "/account/membership/detail/companyId/{companyId}/userId/{userId}")
    public Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(@PathVariable Long companyId, @PathVariable Long userId) {
        log.info("Fetching AccountMembershipDTO for userId: " + userId + " in companyId: " + companyId);
        return accountService.getAccountMembershipDetailByUserId(userId);
    }

}
