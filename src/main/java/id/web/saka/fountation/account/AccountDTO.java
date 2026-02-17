package id.web.saka.fountation.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZonedDateTime;

public record AccountDTO(
        @JsonProperty("accountId") Long id,
        @JsonProperty("userId") Long userId,
        @JsonProperty("accountNumber") String number,
        @JsonProperty("accountType") Account.AccountType type,
        @JsonProperty("accountStatus") Account.AccountStatus status,
        @JsonProperty("accountCreatedAt") Instant createdAt
) { }
