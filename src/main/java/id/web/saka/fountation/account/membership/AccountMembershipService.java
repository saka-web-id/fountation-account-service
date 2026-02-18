package id.web.saka.fountation.account.membership;

import id.web.saka.fountation.account.AccountService;
import id.web.saka.fountation.account.user.AccountUserService;
import id.web.saka.fountation.membership.MembershipService;
import id.web.saka.fountation.util.Env;
import org.slf4j.Logger;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class AccountMembershipService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(AccountMembershipService.class);

    private final AccountService accountService;

    private final AccountUserService accountUserService;

    private final MembershipService membershipService;

    private final AccountMembershipMapper accountMembershipMapper;

    private final ReactiveRedisTemplate<String, AccountMembershipDTO> redisTemplateUserAccountDTO;

    private final Env env;

    public AccountMembershipService(AccountService accountService, AccountUserService accountUserService, MembershipService membershipService, AccountMembershipMapper accountMembershipMapper,
                                    ReactiveRedisTemplate<String, AccountMembershipDTO> redisTemplateUserAccountDTO, Env env) {
        this.accountService = accountService;
        this.accountUserService = accountUserService;
        this.membershipService = membershipService;
        this.accountMembershipMapper = accountMembershipMapper;
        this.redisTemplateUserAccountDTO = redisTemplateUserAccountDTO;
        this.env = env;
    }

    public Mono<AccountMembershipDTO> getAccountMembershipDetailByUserId(Long userId) {
        logger.info("Fetching AccountMembershipDTO for userId: {}", userId);
        String key = "accountMembershipDTO:userId:" + userId;

        return redisTemplateUserAccountDTO.opsForValue().get(key)
            .onErrorResume(e -> {
                logger.warn("Redis unavailable, fallback to DB: {}", e.getMessage());
                return Mono.empty();
            })
            .switchIfEmpty(
                accountUserService.getAccountByUserId(userId).flatMap(accountUser ->
                    accountService.getAccountById(accountUser.getAccountId()).flatMap(account ->
                        membershipService.findMembershipByAccountId(accountUser.getAccountId())
                            .flatMap(membership -> {
                                logger.info("Mapping Account {} and Membership {} to AccountMembershipDTO", account.toString(), membership.toString());

                                return cacheAccountMembershipDTO(key, accountMembershipMapper.toDto(account, membership))
                                        .doOnNext(dto ->
                                                logger.info("Mapped and cached AccountMembershipDTO: {}", dto.toString())
                                        );
                            })
                    )
                )
            );

    }

    private Mono<AccountMembershipDTO> cacheAccountMembershipDTO(String key, AccountMembershipDTO dto) {
        logger.info("Redis cache user {} with dto {} ", key, dto.toString() );

        return redisTemplateUserAccountDTO.opsForValue()
                .set(key, dto, Duration.ofMinutes(env.getFountationServiceRedisStoreDurationInMinutes()))
                .onErrorResume(err -> {
                    logger.warn("Failed to cache in Redis: {}", err.getMessage());
                    return Mono.empty();
                })
                .thenReturn(dto);
    }
}
