package id.web.saka.fountation.account.membership.plan;

import id.web.saka.fountation.account.membership.AccountMembershipDTO;
import id.web.saka.fountation.membership.plan.MembershipPlanDTO;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class, JsonMapper.class, EnumMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
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



    @Mapping(target = "accountCreatedAt", source = "accountCreatedAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipPlan", source = "membershipPlan")
    @Mapping(target = "accountStatus", source = "accountStatus", defaultValue = "AS_INACTIVE", qualifiedByName = "stringToAccountStatus")
    @Mapping(target = "accountType", source = "accountType", defaultValue = "AT_FREE", qualifiedByName = "stringToAccountType")
    @Mapping(target = "membershipStatus", source = "membershipStatus", defaultValue = "MS_INACTIVE", qualifiedByName = "stringToMembershipStatus")
    @Mapping(target = "membershipPlan.name", source = "membershipPlan.name", defaultValue = "MPN_FREE", qualifiedByName = "stringToMembershipPlan")
    @Mapping(target = "membershipPlan.features", source = "membershipPlan.features", qualifiedByName = "jsonToString")
    @Mapping(target = "membershipPlan.createdAt", source = "membershipPlan.createdAt", qualifiedByName = "instantToTimestamp")
    AccountMembershipPlanResponse toProto(AccountMembershipPlanDTO dto);

}
