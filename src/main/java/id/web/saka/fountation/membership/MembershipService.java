package id.web.saka.fountation.membership;

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

    public Mono<Membership> createMembershipForAccountWithPlan(Long accountId, Long planId, Membership.MembershipStatus membershipStatus) {
        return membershipRepository.save(new Membership(accountId, planId, membershipStatus));
    }
}
