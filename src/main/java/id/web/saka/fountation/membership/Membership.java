package id.web.saka.fountation.membership;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.ZonedDateTime;

@Table(value = "membership", schema = "account")
public class Membership {

    public Membership() {
    }

    public Membership(Long accountId, Long planId, MembershipStatus status) {
        this.accountId =  accountId;
        this.planId = planId;
        this.status = status;
    }

    public enum MembershipStatus{ ACTIVE, INACTIVE, PENDING };

    @Id
    @Column("id")
    private Long id;

    @Column("account_id")
    private Long accountId;

    @Column("plan_id")
    private Long planId;

    @Column("status")
    private MembershipStatus status;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
