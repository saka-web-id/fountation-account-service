package id.web.saka.fountation.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("account")
public class Account{

    public enum AccountStatus{ ACTIVE, INACTIVE, DISABLE };

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    private String accountNumber;

    private AccountStatus status;

    private OffsetDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
