package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.account.membership.AccountMembershipDTO;
import id.web.saka.fountation.account.membership.AccountMembershipMapper;
import id.web.saka.fountation.account.user.AccountUserService;
import id.web.saka.fountation.membership.MembershipService;
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

    private final AccountService accountService;

    private final AccountUserService accountUserService;

    private final AccountMembershipMapper accountMembershipMapper;

    private final AccountMembershipPlanMapper accountMembershipPlanMapper;

    private final MembershipService membershipService;

    private final MembershipPlanService membershipPlanService;

    private final ReactiveRedisTemplate<String, AccountMembershipPlanDTO> redisTemplateAccountMembershipPlanDTO;

    private final Env env;

    public AccountMembershipPlanService(
            AccountService accountService,
            AccountUserService accountUserService,
            AccountMembershipMapper accountMembershipMapper,
            AccountMembershipPlanMapper accountMembershipPlanMapper,
            MembershipService membershipService,
            MembershipPlanService membershipPlanService,
            ReactiveRedisTemplate<String, AccountMembershipPlanDTO> redisTemplateAccountMembershipPlanDTO,
            Env env) {
        this.accountService = accountService;
        this.accountUserService = accountUserService;
        this.accountMembershipMapper = accountMembershipMapper;
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
        this.membershipService = membershipService;
        this.membershipPlanService = membershipPlanService;
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
                        getAccountMembershipDetailByUserId(valueUserId)
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

    private Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(Long userId) {
        log.info("Fetching AccountMembershipDTO for userId: {}", userId);
        String key = "accountMembershipDTO:userId:" + userId;

        return accountUserService.getAccountByUserId(userId)
                .flatMap(accountUser -> accountService.getAccountById(accountUser.getAccountId())
                        .flatMap(account -> membershipService.findMembershipByAccountId(accountUser.getAccountId())
                                .map(membership -> {
                                    log.info("Mapping Account {} and Membership {} to AccountMembershipDTO", account, membership);
                                    return accountMembershipMapper.toDto(account, membership);
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
