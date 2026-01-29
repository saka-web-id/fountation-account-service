package id.web.saka.fountation.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Table(value = "account", schema = "account")
public class Account{

    public enum AccountStatus{ ACTIVE, INACTIVE, DISABLE };

    public enum AccountType{ SAVINGS, CHECKING, BUSINESS, JOINT };

    @Id
    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("account_number")
    private String number;

    @Column("account_type")
    private AccountType type;

    @Column("status")
    private AccountStatus status;

    @Column("created_at")
    private ZonedDateTime createdAt;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
