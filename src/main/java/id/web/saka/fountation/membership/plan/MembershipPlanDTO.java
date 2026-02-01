package id.web.saka.fountation.membership.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;

public record MembershipPlanDTO (
    @JsonProperty("membershipPlanId") Long id,
    @JsonProperty("membershipPlanName") String name,
    @JsonProperty("membershipPlanPrice") Double price,
    @JsonProperty("membershipPlanBillingCycle") String billingCycle,
    @JsonProperty("membershipPlanFeatures") JsonNode features,
    @JsonProperty("membershipPlanCompanyId") Long companyId,
    @JsonProperty("membershipPlanCreatedAt") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX") ZonedDateTime createdAt

) {}
