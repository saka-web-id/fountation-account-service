package id.web.saka.fountation.membership.plan;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table(value = "membership_plan", schema = "account")
public class MembershipPlan {

    @Id
    @Column("id")
    private Long id;

    @Column("company_id")
    private Long companyId;
    @Column("name")
    private String name;
    @Column("price")
    private Double price;
    @Column("billing_cycle")
    private String billingCycle;
    @Column("features")
    private String features;
    @Column("created_at")
    private ZonedDateTime createdAt;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
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
