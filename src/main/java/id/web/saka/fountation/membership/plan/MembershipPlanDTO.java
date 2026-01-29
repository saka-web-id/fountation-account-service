package id.web.saka.fountation.membership.plan;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MembershipPlanDTO (
    @JsonProperty("membershipPlanId") Long id,
    @JsonProperty("membershipPlanName") String name,
    @JsonProperty("membershipPlanPrice") Double price,
    @JsonProperty("membershipPlanBillingCycle") String billingCycle,
    @JsonProperty("membershipPlanFeatures") String features,
    @JsonProperty("membershipPlanCreatedAt") String createdAt

) {}
