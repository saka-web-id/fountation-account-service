package id.web.saka.fountation.membership.billing.cycle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record BillingCycleDTO (
        @JsonProperty("billingCycleId") Long id,
        @JsonProperty("billingCycleCompanyId") Long companyId,
        @JsonProperty("billingCycleName") String name,
        @JsonProperty("billingCycleDuration") Integer duration,
        @JsonProperty("billingCycleCreatedAt") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX") ZonedDateTime createdAt
) {
}
