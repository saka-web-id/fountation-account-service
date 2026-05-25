package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.account.membership.AccountMembershipDTO;
import id.web.saka.fountation.account.membership.AccountMembershipMapper;
import id.web.saka.fountation.account.user.AccountUserService;
import id.web.saka.fountation.configbase.fountation.FountationProperties;
import id.web.saka.fountation.membership.MembershipService;
import id.web.saka.fountation.membership.plan.MembershipPlanService;
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

    private final FountationProperties fountationProperties;

    public AccountMembershipPlanService(
            AccountService accountService,
            AccountUserService accountUserService,
            AccountMembershipMapper accountMembershipMapper,
            AccountMembershipPlanMapper accountMembershipPlanMapper,
            MembershipService membershipService,
            MembershipPlanService membershipPlanService,
            ReactiveRedisTemplate<String, AccountMembershipPlanDTO> redisTemplateAccountMembershipPlanDTO,
            FountationProperties fountationProperties) {
        this.accountService = accountService;
        this.accountUserService = accountUserService;
        this.accountMembershipMapper = accountMembershipMapper;
        this.accountMembershipPlanMapper = accountMembershipPlanMapper;
        this.membershipService = membershipService;
        this.membershipPlanService = membershipPlanService;
        this.redisTemplateAccountMembershipPlanDTO = redisTemplateAccountMembershipPlanDTO;
        this.fountationProperties = fountationProperties;
    }

    public Mono<AccountMembershipPlanDTO> getAccountMembershipPlanDetailByUserId(
            Long companyId, Long userId, Long valueUserId) {

        String key = "accountMembershipPlanDTO:userId:" + valueUserId;

        return redisTemplateAccountMembershipPlanDTO.opsForValue()
                .get(key)
                .doOnNext(dto -> log.info("[getAccountMembershipPlanDetailByUserId] Cache hit for account membership plan detail with key: {}", key))
                .onErrorResume(e -> {
                    log.warn("[getAccountMembershipPlanDetailByUserId] Redis unavailable while fetching account membership plan detail, falling back to database. Error: {}", e.getMessage());
                    return Mono.empty();
                })
                .switchIfEmpty(
                        getAccountMembershipDetailByUserId(valueUserId)
                                .flatMap(accountMembershipDTO ->
                                        membershipPlanService
                                                .getMembershipPlanByMembershipPlanId(accountMembershipDTO.membershipPlanId())
                                                .doOnNext(membershipPlanDTO -> log.info("[getAccountMembershipPlanDetailByUserId] Successfully retrieved membership plan detail from DB for plan ID: {}", accountMembershipDTO.membershipPlanId()))
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

    public reactor.core.publisher.Flux<id.web.saka.fountation.membership.plan.MembershipPlanDTO> getMembershipPlanListByCompanyId(Long companyId, Long userId, Long valueCompanyId) {
        log.info("[getMembershipPlanListByCompanyId] Fetching membership plan list for company ID: {} requested by user ID: {}", valueCompanyId, userId);
        return membershipPlanService.getMembershipPlanListByCompanyId(companyId, userId, valueCompanyId);
    }

    public Mono<AccountMembershipPlanDTO> updateAccountMembershipPlan(Long companyId, Long userId, Long valueUserId, id.web.saka.fountation.account.Account.AccountStatus accountStatus, id.web.saka.fountation.membership.Membership.MembershipStatus membershipStatus, Long membershipPlanId) {
        log.info("[updateAccountMembershipPlan] Updating account and membership status for target user ID: {} initiated by user ID: {} in company ID: {}", valueUserId, userId, companyId);

        return accountUserService.getAccountByUserId(valueUserId)
                .flatMap(accountUser ->
                        accountService.getAccountById(accountUser.getAccountId())
                                .flatMap(account -> {
                                    account.setStatus(accountStatus);
                                    return accountService.createAccount(account);
                                })
                                .doOnNext(updatedAccount -> log.info("[updateAccountMembershipPlan] Successfully updated account status for account ID: {} to {}", updatedAccount.getId(), accountStatus))
                                .flatMap(updatedAccount ->
                                        membershipService.findMembershipByAccountId(updatedAccount.getId())
                                                .flatMap(membership -> {
                                                    membership.setStatus(membershipStatus);
                                                    membership.setPlanId(membershipPlanId);
                                                    return membershipService.saveMembership(membership);
                                                })
                                                .doOnNext(savedMembership -> log.info("[updateAccountMembershipPlan] Successfully updated membership status for account ID: {} to plan ID: {}", savedMembership.getAccountId(), membershipPlanId))
                                )
                )
                .flatMap(savedMembership -> {
                    // Evict cache
                    String key = "accountMembershipPlanDTO:userId:" + valueUserId;
                    return redisTemplateAccountMembershipPlanDTO.opsForValue().delete(key)
                            .doOnNext(deleted -> log.info("[updateAccountMembershipPlan] Evicted cache for key: {} result: {}", key, deleted))
                            .then(getAccountMembershipPlanDetailByUserId(companyId, userId, valueUserId));
                });
    }

    private Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(Long userId) {
        log.info("[getAccountMembershipDetailByUserId] Fetching detailed account membership for user ID: {}", userId);
        String key = "accountMembershipDTO:userId:" + userId;

        return accountUserService.getAccountByUserId(userId)
                .flatMap(accountUser -> accountService.getAccountById(accountUser.getAccountId())
                        .flatMap(account -> membershipService.findMembershipByAccountId(accountUser.getAccountId())
                                .map(membership -> {
                                    log.info("[getAccountMembershipDetailByUserId] Mapping account ID: {} and membership ID: {} to AccountMembershipDTO", account.getId(), membership.getId());
                                    return accountMembershipMapper.toDto(account, membership);
                                })
                        )
                );
    }

    private Mono<AccountMembershipPlanDTO> cacheAccountMembershipDTO(String key, AccountMembershipPlanDTO dto) {
        log.info("[cacheAccountMembershipDTO] Caching account membership plan detail in Redis for key: {}", key);

        return redisTemplateAccountMembershipPlanDTO.opsForValue()
                .set(key, dto, Duration.ofMinutes(fountationProperties.getService().getRedis().getStore().getDuration().getMinutes()))
                .doOnSuccess(v -> log.info("[cacheAccountMembershipDTO] Successfully cached data in Redis for key: {}", key))
                .onErrorResume(err -> {
                    log.warn("[cacheAccountMembershipDTO] Failed to cache data in Redis for key: {}. Error: {}", key, err.getMessage());
                    return Mono.empty();
                })
                .thenReturn(dto);
    }

}
