package id.web.saka.fountation.account;

import id.web.saka.fountation.account.membership.Membership;
import id.web.saka.fountation.account.membership.MembershipService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final MembershipService membershipService;

    public AccountService(AccountRepository accountRepository, MembershipService membershipService) {
        this.accountRepository = accountRepository;
        this.membershipService = membershipService;
    }

    public Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(Long userId) {

        Mono<Account> accountMono = this.accountRepository.findFirstByUserId(userId);
        Mono<Membership> membershipMono = this.membershipService.findMembershipByAccountId(accountMono.map(Account::getId));

        return Mono.zip(accountMono, membershipMono, (account, membership) -> {
            AccountMembershipDTO dto = new AccountMembershipDTO();
            dto.setAccountStatus(account.getStatus());
            dto.setAccountNumber(account.getAccountNumber());
            dto.setCreatedAt(account.getCreatedAt());
            dto.setMembershipType(membership.getType());
            dto.setMembershipStartDate(membership.getStartDate());
            dto.setMembershipEndDate(membership.getEndDate());
            return dto;
        });
    }
}
