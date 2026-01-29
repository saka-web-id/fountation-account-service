package id.web.saka.fountation.account.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.membership.Membership;
import java.time.ZonedDateTime;

public record AccountMembershipDTO (
        @JsonProperty("accountNumber") String accountNumber,
        @JsonProperty("accountStatus") Account.AccountStatus accountStatus,
        @JsonProperty("accountType") Account.AccountType accountType,
        @JsonProperty("accountCreatedAt") ZonedDateTime accountCreatedAt,
        @JsonProperty("membershipPlanId") Long membershipPlanId,
        @JsonProperty("membershipStatus") Membership.MembershipStatus membershipStatus,
        @JsonProperty("membershipStartDate") ZonedDateTime membershipStartDate,
        @JsonProperty("membershipEndDate") ZonedDateTime membershipEndDate
) {
}
