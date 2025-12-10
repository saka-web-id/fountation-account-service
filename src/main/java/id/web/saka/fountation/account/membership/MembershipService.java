package id.web.saka.fountation.account.membership;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MembershipService {
    
    private final MembershipRepository membershipRepository;
    
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Mono<Membership> findMembershipByAccountId(Long accountId) {
        return membershipRepository.findByAccountId(accountId);
    }
}
