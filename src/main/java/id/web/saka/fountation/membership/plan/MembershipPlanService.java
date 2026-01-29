package id.web.saka.fountation.membership.plan;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;

    private final MembershipPlanMapper membershipPlanMapper;

    public MembershipPlanService(MembershipPlanRepository membershipPlanRepository, MembershipPlanMapper membershipPlanMapper) {
        this.membershipPlanRepository = membershipPlanRepository;
        this.membershipPlanMapper = membershipPlanMapper;
    }

    public Flux<MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId) {
        return membershipPlanRepository.findByCompanyId(valueCompanyId).map(obj -> membershipPlanMapper.toDto((MembershipPlan) obj));
    }

    public Mono<? extends MembershipPlanDTO> saveMembershipPlan(Long companyId, Long userId, MembershipPlanDTO membershipPlan) {

        return membershipPlanRepository.save(membershipPlanMapper.toEntity(membershipPlan))
                .map(membershipPlanMapper::toDto);
    }

    public Mono<MembershipPlanDTO> getMembershipPlanByMembershipPlanId(Long planId) {
        return membershipPlanRepository.findById(planId)
                .map(membershipPlanMapper::toDto);
    }
}
