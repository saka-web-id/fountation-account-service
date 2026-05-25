package id.web.saka.fountation.membership.plan;

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
        log.info("[getMembershipPlanListByCompanyId] Fetching membership plan list for company ID: {} requested by user ID: {}", valueCompanyId, userId);
        return membershipPlanRepository.findByCompanyId(valueCompanyId).map(obj -> membershipPlanMapper.toDto((MembershipPlan) obj));
    }

    public Mono<? extends MembershipPlanDTO> saveMembershipPlan(Long companyId, Long userId, MembershipPlanDTO membershipPlan) {

        log.info("[saveMembershipPlan] Saving membership plan for company ID: {} initiated by user ID: {}", companyId, userId);

        return membershipPlanRepository.save(membershipPlanMapper.toEntity(membershipPlan))
                .doOnNext(savedMembershipPlan -> log.info("[saveMembershipPlan] Successfully saved membership plan ID: {} with name: {}", savedMembershipPlan.getId(), savedMembershipPlan.getName()))
                .map(membershipPlanMapper::toDto);
    }

    public Mono<MembershipPlanDTO> getMembershipPlanByMembershipPlanId(Long planId) {
        log.info("[getMembershipPlanByMembershipPlanId] Fetching membership plan detail for plan ID: {}", planId);
        return membershipPlanRepository.findById(planId)
                .map(membershipPlanMapper::toDto);
    }

    public Mono<MembershipPlan> createDefaultMembershipPlanForNewCompany(CompanyDTO company) {
        log.info("[createDefaultMembershipPlanForNewCompany] Creating default FREE membership plan for new company: {}", company.name());

        MembershipPlan defaultPlan = new MembershipPlan();
        defaultPlan.setCompanyId(company.id());
        defaultPlan.setName(MembershipPlan.MembershipPlanName.FREE);
        defaultPlan.setBillingCycle("YEARLY");
        defaultPlan.setFeatures(null);
        defaultPlan.setPrice(0.0);

        return membershipPlanRepository.save(defaultPlan)
                .doOnNext(savedPlan -> log.info("[createDefaultMembershipPlanForNewCompany] Successfully created default membership plan ID: {} for company ID: {}", savedPlan.getId(), company.id()));
    }
}
