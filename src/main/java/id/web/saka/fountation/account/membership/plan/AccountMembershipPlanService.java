package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.membership.AccountMembershipService;
import id.web.saka.fountation.membership.plan.MembershipPlanService;
import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class AccountMembershipPlanService {

    Logger log = org.slf4j.LoggerFactory.getLogger(AccountMembershipPlanService.class);

    private final AccountMembershipService accountMembershipService;

    private final MembershipPlanService membershipPlanService;

    private final AccountMembershipPlanMapper accountMembershipPlanMapper;

    private final ReactiveRedisTemplate<String, AccountMembershipPlanDTO> redisTemplateAccountMembershipPlanDTO;

    private final Env env;

    public AccountMembershipPlanService(AccountMembershipService accountMembershipService, MembershipPlanService membershipPlanService, AccountMembershipPlanMapper accountMembershipPlanMapper,
                                        ReactiveRedisTemplate<String, AccountMembershipPlanDTO> redisTemplateAccountMembershipPlanDTO, Env env) {
        this.accountMembershipService = accountMembershipService;
        this.membershipPlanService = membershipPlanService;
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
        this.redisTemplateAccountMembershipPlanDTO = redisTemplateAccountMembershipPlanDTO;
        this.env = env;
    }

    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(
            Long companyId, Long userId, Long valueUserId) {

        String key = "accountMembershipPlanDTO:userId:" + valueUserId;

        return redisTemplateAccountMembershipPlanDTO.opsForValue()
                .get(key)
                .onErrorResume(e -> {
                    log.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                    return Mono.empty();
                })
                .switchIfEmpty(
                        accountMembershipService.getAccountMembershipDetailByUserId(valueUserId)
                                .flatMap(accountMembershipDTO ->
                                        membershipPlanService
                                                .getMembershipPlanByMembershipPlanId(accountMembershipDTO.membershipPlanId())
                                                .flatMap(membershipPlanDTO -> {
                                                    AccountMembershipPlanDTO dto =
                                                            accountMembershipPlanMapper.toDto(
                                                                    accountMembershipDTO,
                                                                    membershipPlanDTO
                                                            );
                                                    return cacheAccountMembershipDTO(key, dto);
                                                })
                                )
                );
    }

    private Mono<AccountMembershipPlanDTO> cacheAccountMembershipDTO(String key, AccountMembershipPlanDTO dto) {
        log.info("Redis cache user {} with dto {} ", key, dto.toString() );

        return redisTemplateAccountMembershipPlanDTO.opsForValue()
                .set(key, dto, Duration.ofMinutes(env.getFountationServiceRedisStoreDurationInMinutes()))
                .onErrorResume(err -> {
                    log.warn("Failed to cache in Redis: {}", err.getMessage());
                    return Mono.empty();
                })
                .thenReturn(dto);
    }

}
