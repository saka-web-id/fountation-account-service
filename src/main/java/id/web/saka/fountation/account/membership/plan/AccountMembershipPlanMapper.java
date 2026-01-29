package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.membership.AccountMembershipDTO;
import id.web.saka.fountation.membership.plan.MembershipPlanDTO;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface AccountMembershipPlanMapper {
    @Mapping(source = "accountMembership.accountNumber", target = "accountNumber")
    @Mapping(source = "accountMembership.accountStatus", target = "accountStatus")
    @Mapping(source = "accountMembership.accountType", target = "accountType")
    @Mapping(source = "accountMembership.accountCreatedAt", target = "accountCreatedAt")
    @Mapping(source = "accountMembership.membershipStatus", target = "membershipStatus")
    @Mapping(source = "accountMembership.membershipStartDate", target = "membershipStartDate")
    @Mapping(source = "accountMembership.membershipEndDate", target = "membershipEndDate")
    @Mapping(source = "accountMembership.membershipPlanId", target = "membershipPlanId")
    @Mapping(source = "membershipPlan", target = "membershipPlan")
    AccountMembershipPlanDTO toDto(AccountMembershipDTO accountMembership, MembershipPlanDTO membershipPlan);

}
