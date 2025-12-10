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
        return this.accountRepository.findFirstByUserId(userId)
                .flatMap(account ->
                        this.membershipService.findMembershipByAccountId(account.getId())
                                .flatMap(membership -> {
                                    AccountMembershipDTO dto = new AccountMembershipDTO();

                                    dto.setAccountNumber(account.getAccountNumber());
                                    dto.setAccountStatus(account.getStatus());
                                    dto.setMembershipType(membership.getType());
                                    dto.setMembershipStatus(membership.getStatus());
                                    dto.setCreatedAt(account.getCreatedAt());
                                    dto.setMembershipStartDate(membership.getStartDate());
                                    dto.setMembershipEndDate(membership.getEndDate());

                                    return Mono.just(dto);
                                })
                );
    }

}
