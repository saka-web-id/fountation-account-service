package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.membership.Membership;
import id.web.saka.fountation.organization.company.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MembershipPlanService {

    Logger log = LoggerFactory.getLogger(MembershipPlanService.class);

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

        log.info("Saving MembershipPlan for companyId: " + companyId + ", userId: " + userId + ", membershipPlan: " + membershipPlan.features().textValue());

        return membershipPlanRepository.save(membershipPlanMapper.toEntity(membershipPlan))
                .doOnNext(savedMembershipPlan -> log.info("Saved MembershipPlan: {}", savedMembershipPlan.getFeatures().textValue()))
                .map(membershipPlanMapper::toDto);
    }

    public Mono<MembershipPlanDTO> getMembershipPlanByMembershipPlanId(Long planId) {
        return membershipPlanRepository.findById(planId)
                .map(membershipPlanMapper::toDto);
    }

    public Mono<MembershipPlan> createDefaultMembershipPlanForNewCompany(CompanyDTO company) {

        MembershipPlan defaultPlan = new MembershipPlan();
        defaultPlan.setCompanyId(company.id());
        defaultPlan.setName(MembershipPlan.MembershipPlanName.FREE);
        defaultPlan.setBillingCycle("YEARLY");
        defaultPlan.setFeatures(null);
        defaultPlan.setPrice(0.0);

        return membershipPlanRepository.save(defaultPlan);
    }
}
