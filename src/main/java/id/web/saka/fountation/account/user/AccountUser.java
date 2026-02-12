package id.web.saka.fountation.account.user;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.ZonedDateTime;

@Table(name = "account_user", schema = "account")
public class AccountUser {

    public AccountUser() {
    }

    public AccountUser(Long accountId, Long userId) {
        this.accountId = accountId;
        this.userId = userId;
    }

    @Column("account_id")
    private Long accountId;

    @Column("user_id")
    private Long userId;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AccountUser{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
