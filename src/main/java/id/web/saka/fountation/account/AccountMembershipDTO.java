package id.web.saka.fountation.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.account.membership.Membership;

import java.time.OffsetDateTime;

public class AccountMembershipDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountStatus")
    private Account.AccountStatus accountStatus;

    @JsonProperty("membershipType")
    private Membership.MembershipType membershipType;

    @JsonProperty("membershipStatus")
    private Membership.MembershipStatus membershipStatus;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private OffsetDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private OffsetDateTime membershipEndDate;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account.AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Account.AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Membership.MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Membership.MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public Membership.MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(Membership.MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(OffsetDateTime membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public OffsetDateTime getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(OffsetDateTime membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }
}
