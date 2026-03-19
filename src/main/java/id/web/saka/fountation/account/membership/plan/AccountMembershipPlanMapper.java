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
    @Mapping(source = "membershipPlan.id", target = "membershipPlan.id")
    @Mapping(source = "membershipPlan.name", target = "membershipPlan.name")
    @Mapping(source = "membershipPlan.price", target = "membershipPlan.price")
    @Mapping(source = "membershipPlan.billingCycle", target = "membershipPlan.billingCycle")
    @Mapping(source = "membershipPlan.features", target = "membershipPlan.features")
    @Mapping(source = "membershipPlan.createdAt", target = "membershipPlan.createdAt", qualifiedByName = "toInstant")
    AccountMembershipPlanDTO toDto(AccountMembershipDTO accountMembership, MembershipPlanDTO membershipPlan);

}
