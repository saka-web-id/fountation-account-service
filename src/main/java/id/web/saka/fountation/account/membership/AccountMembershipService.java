package id.web.saka.fountation.account.membership;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.account.user.AccountUserService;
import id.web.saka.fountation.membership.MembershipService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountMembershipService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(AccountMembershipService.class);

    private final AccountService accountService;

    private final AccountUserService accountUserService;

    private final MembershipService membershipService;

    private final AccountMembershipMapper accountMembershipMapper;

    public AccountMembershipService(AccountService accountService, AccountUserService accountUserService, MembershipService membershipService, AccountMembershipMapper accountMembershipMapper) {
        this.accountService = accountService;
        this.accountUserService = accountUserService;
        this.membershipService = membershipService;
        this.accountMembershipMapper = accountMembershipMapper;
    }

    public Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(Long userId) {
        logger.info("Fetching AccountMembershipDTO for userId: {}", userId);
        return accountUserService.getAccountByUserId(userId).flatMap(accountUser ->
                accountService.getAccountById(accountUser.getAccountId()).flatMap(account ->
                        membershipService.findMembershipByAccountId(accountUser.getAccountId())
                                .flatMap(membership -> {
                                    logger.info("Mapping Account {} and Membership {} to AccountMembershipDTO", account.toString(), membership.toString());

                                    return Mono.just(accountMembershipMapper.toDto(account, membership)).doOnNext(dto ->
                                            logger.info("Mapped AccountMembershipDTO: {}", dto.toString())
                                    );
                                })
                )
        );
    }
}
