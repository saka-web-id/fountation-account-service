package id.web.saka.fountation.config;

import id.web.saka.fountation.account.membership.AccountMembershipDTO;
import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public ReactiveRedisTemplate<String, AccountMembershipDTO> reactiveRedisTemplateAccountMembershipDTO (
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        Jackson2JsonRedisSerializer<AccountMembershipDTO> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, AccountMembershipDTO.class);

        RedisSerializationContext<String, AccountMembershipDTO> context =
                RedisSerializationContext
                        .<String, AccountMembershipDTO>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, AccountMembershipPlanDTO> reactiveRedisTemplateAccountMembershipPlanDTO (
            ReactiveRedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper) {

        Jackson2JsonRedisSerializer<AccountMembershipPlanDTO> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, AccountMembershipPlanDTO.class);

        RedisSerializationContext<String, AccountMembershipPlanDTO> context =
                RedisSerializationContext
                        .<String, AccountMembershipPlanDTO>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
