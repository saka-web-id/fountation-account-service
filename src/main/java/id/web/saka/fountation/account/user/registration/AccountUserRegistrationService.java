package id.web.saka.fountation.account.user.registration;

import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.account.AccountMapper;
import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.account.user.AccountUserService;
import id.web.saka.fountation.membership.Membership;
import id.web.saka.fountation.membership.MembershipService;
import id.web.saka.fountation.membership.plan.MembershipPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountUserRegistrationService {

    Logger log = LoggerFactory.getLogger(AccountUserRegistrationService.class);

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    private final AccountUserService accountUserService;

    private final MembershipPlanService membershipPlanService;

    private final MembershipService membershipService;

    public AccountUserRegistrationService(AccountService accountService, AccountMapper accountMapper, AccountUserService accountUserService, MembershipPlanService membershipPlanService, MembershipService membershipService) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.accountUserService = accountUserService;
        this.membershipPlanService = membershipPlanService;
        this.membershipService = membershipService;
    }


    public Mono<AccountUserRegistrationDTO> assignAccountToNewUser(AccountUserRegistrationDTO accountUserRegistrationDTO) {
        log.info("[assignAccountToNewUser] Assigning account to new user with email: {}", accountUserRegistrationDTO.user().email());

        // create new account with status INACTIVE and account type FREE
        Account account = accountUserRegistrationDTO.account() != null
                ? accountMapper.toEntity(accountUserRegistrationDTO.account())
                : new Account();

        account.setId(null);
        account.setNumber("123456789101112"); // TODO: make standard Account Number
        account.setStatus(Account.AccountStatus.INACTIVE);
        account.setType(Account.AccountType.FREE);

        return accountService.createAccount(account)
                .doOnNext(createdAccount -> log.info("[assignAccountToNewUser] Created new inactive account for user with ID: {}", createdAccount.getId()))
                .flatMap(createdAccount ->
                        accountUserService.assignAccountToNewUser(accountUserRegistrationDTO.user(), createdAccount)
                                .doOnNext(accountUser -> log.info("[assignAccountToNewUser] Successfully mapped user ID: {} to account ID: {}", accountUser.getUserId(), createdAccount.getId()))
                                .flatMap(accountUser ->
                                        membershipPlanService.createDefaultMembershipPlanForNewCompany(accountUserRegistrationDTO.company())
                                                .doOnNext(membershipPlan -> log.info("[assignAccountToNewUser] Created default membership plan for new company: {}", accountUserRegistrationDTO.company().name()))
                                                .flatMap(membershipPlan ->
                                                        membershipService.createMembershipForAccountWithPlan(
                                                                        createdAccount.getId(),
                                                                        membershipPlan.getId(),
                                                                        Membership.MembershipStatus.INACTIVE
                                                                )
                                                                .doOnNext(membership -> log.info("[assignAccountToNewUser] Successfully created inactive membership for account ID: {} with plan ID: {}", createdAccount.getId(), membershipPlan.getId()))
                                                                .thenReturn(new AccountUserRegistrationDTO(
                                                                        accountUserRegistrationDTO.user(),
                                                                        accountMapper.toDTO(createdAccount),
                                                                        accountUserRegistrationDTO.company(),
                                                                        accountUserRegistrationDTO.department()
                                                                ))
                                                )
                                )
                );
    }
}
