package id.web.saka.fountation.account;

import id.web.saka.fountation.account.membership.plan.AccountMembershipPlanDTO;
import id.web.saka.fountation.account.user.registration.AccountUserRegistrationDTO;
import id.web.saka.fountation.membership.Membership;
import id.web.saka.fountation.membership.plan.MembershipPlanGrpcMapper;
import id.web.saka.fountation.organization.company.CompanyGrpcMapper;
import id.web.saka.fountation.organization.department.DepartmentGrpcMapper;
import id.web.saka.fountation.user.UserGrpcMapper;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class,
        UserGrpcMapper.class,
        CompanyGrpcMapper.class,
        DepartmentGrpcMapper.class,
        MembershipPlanGrpcMapper.class
})
public interface AccountGrpcMapper {

    @Mapping(target = "accountCreatedAt", source = "accountCreatedAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipStartDate", source = "membershipStartDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipEndDate", source = "membershipEndDate", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "membershipPlan", source = "membershipPlan")
    @Mapping(target = "accountStatus", source = "accountStatus", defaultValue = "AS_INACTIVE")
    @Mapping(target = "accountType", source = "accountType", defaultValue = "AT_FREE")
    @Mapping(target = "membershipStatus", source = "membershipStatus", defaultValue = "MS_INACTIVE")
    AccountMembershipPlanResponse toProto(AccountMembershipPlanDTO dto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    UserRegistrationResponse toProto(AccountUserRegistrationDTO dto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    AccountUserRegistrationDTO toDTO(UserRegistrationRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "accountNumber", source = "number")
    @Mapping(target = "accountStatus", source = "status")
    @Mapping(target = "accountType", source = "type")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToTimestamp")
    AccountProto toProto(AccountDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "number", source = "accountNumber")
    @Mapping(target = "status", source = "accountStatus")
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toInstant")
    AccountDTO toDTO(AccountProto proto);

    // --- AccountStatus Mapping ---

    @ValueMapping(source = "ACTIVE", target = "AS_ACTIVE")
    @ValueMapping(source = "INACTIVE", target = "AS_INACTIVE")
    @ValueMapping(source = "DISABLE", target = "AS_DISABLE")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "AS_INACTIVE")
    AccountStatus toAccountStatusProto(Account.AccountStatus status);

    @ValueMapping(source = "AS_ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "AS_INACTIVE", target = "INACTIVE")
    @ValueMapping(source = "AS_DISABLE", target = "DISABLE")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "INACTIVE")
    Account.AccountStatus toAccountStatusEntity(AccountStatus status);

    // --- AccountType Mapping ---

    @ValueMapping(source = "FREE", target = "AT_FREE")
    @ValueMapping(source = "PREMIUM", target = "AT_PREMIUM")
    @ValueMapping(source = "ENTERPRISE", target = "AT_ENTERPRISE")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "AT_FREE")
    AccountType toAccountTypeProto(Account.AccountType type);

    @ValueMapping(source = "AT_FREE", target = "FREE")
    @ValueMapping(source = "AT_PREMIUM", target = "PREMIUM")
    @ValueMapping(source = "AT_ENTERPRISE", target = "ENTERPRISE")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "FREE")
    Account.AccountType toAccountTypeEntity(AccountType type);

    // --- MembershipStatus Mapping ---

    @ValueMapping(source = "ACTIVE", target = "MS_ACTIVE")
    @ValueMapping(source = "INACTIVE", target = "MS_INACTIVE")
    @ValueMapping(source = "PENDING", target = "MS_PENDING")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "MS_INACTIVE")
    MembershipStatus toMembershipStatusProto(Membership.MembershipStatus status);

    @ValueMapping(source = "MS_ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "MS_INACTIVE", target = "INACTIVE")
    @ValueMapping(source = "MS_PENDING", target = "PENDING")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "INACTIVE")
    Membership.MembershipStatus toMembershipStatusEntity(MembershipStatus status);
}
