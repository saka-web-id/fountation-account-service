package id.web.saka.fountation.config;

import id.web.saka.fountation.util.converter.JsonNodeReadConverter;
import id.web.saka.fountation.util.converter.JsonNodeWriteConverter;
import id.web.saka.fountation.util.converter.account.AccountStatusReadConverter;
import id.web.saka.fountation.util.converter.account.AccountStatusWriteConverter;
import id.web.saka.fountation.util.converter.account.AccountTypeReadConverter;
import id.web.saka.fountation.util.converter.account.AccountTypeWriteConverter;
import id.web.saka.fountation.util.converter.account.activity.ActivityActionReadConverter;
import id.web.saka.fountation.util.converter.account.activity.ActivityActionWriteConverter;
import id.web.saka.fountation.util.converter.account.membership.MembershipStatusReadConverter;
import id.web.saka.fountation.util.converter.account.membership.MembershipStatusWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.Arrays;


@Configuration
@EnableR2dbcAuditing(dateTimeProviderRef = "dateTimeProvider")
public class R2dbcConfig {

    // 2. Add this Bean to provide ZonedDateTime instead of LocalDateTime
    @Bean(name = "dateTimeProvider")
    public org.springframework.data.auditing.DateTimeProvider dateTimeProvider() {
        return () -> java.util.Optional.of(java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Jakarta")));
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.NONE,
                Arrays.asList(
                        new AccountStatusReadConverter(),
                        new AccountStatusWriteConverter(),
                        new AccountTypeReadConverter(),
                        new AccountTypeWriteConverter(),
                        new MembershipStatusReadConverter(),
                        new MembershipStatusWriteConverter(),
                        new ActivityActionReadConverter(),
                        new ActivityActionWriteConverter(),
                        new JsonNodeReadConverter(),
                        new JsonNodeWriteConverter()
                )
        );
    }
}

