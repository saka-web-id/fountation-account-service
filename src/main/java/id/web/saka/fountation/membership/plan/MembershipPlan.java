package id.web.saka.fountation.membership.plan;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(value = "membership_plan", schema = "account")
public class MembershipPlan {

    public enum MembershipPlanName { FREE, BASIC, PREMIUM, ENTERPRISE }

    @Id
    @Column("id")
    private Long id;

    @Column("company_id")
    private Long companyId;
    @Column("name")
    private MembershipPlanName name;
    @Column("price")
    private Double price;
    @Column("billing_cycle")
    private String billingCycle;
    @Column("features")
    private JsonNode features;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public MembershipPlanName getName() {
        return name;
    }

    public void setName(MembershipPlanName name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public JsonNode getFeatures() {
        return features;
    }

    public void setFeatures(JsonNode features) {
        this.features = features;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MembershipPlan{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", billingCycle='" + billingCycle + '\'' +
                ", features='" + features + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
