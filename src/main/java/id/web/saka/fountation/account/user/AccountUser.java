package id.web.saka.fountation.account.user;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("account_user")
public class AccountUser {

    @Column("account_id")
    private Long accountId;

    @Column("user_id")
    private Long userId;

    @Column("created_at")
    private ZonedDateTime createdAt;


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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
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
