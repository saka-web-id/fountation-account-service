package id.web.saka.fountation.account.membership;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(value = "membership", schema = "account")
public class Membership {

    public enum MembershipType{ FREE, STANDARD, PREMIUM, VIP };

    public enum MembershipStatus{ ACTIVE, INACTIVE, PENDING };

    @Column("id")
    private Long id;

    @Column("account_id")
    private Long accountId;

    @Column("type")
    private MembershipType type;

    @Column("status")
    private MembershipStatus status;

    @Column("created_at")
    private OffsetDateTime createdAt;

    @Column("start_date")
    private OffsetDateTime startDate;

    @Column("end_date")
    private OffsetDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }
}
