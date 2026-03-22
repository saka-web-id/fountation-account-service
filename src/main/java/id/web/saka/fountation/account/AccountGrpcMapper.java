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
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountGrpcMapper {

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
    @ValueMapping(source = "DISABLED", target = "AS_DISABLED")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "AS_INACTIVE")
    @ValueMapping(source = MappingConstants.NULL, target = "AS_INACTIVE")
    AccountStatus toAccountStatusProto(Account.AccountStatus status);

    @ValueMapping(source = "AS_ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "AS_INACTIVE", target = "INACTIVE")
    @ValueMapping(source = "AS_DISABLED", target = "DISABLED")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "INACTIVE")
    Account.AccountStatus toAccountStatusEntity(AccountStatus status);

    // --- AccountType Mapping ---

    @ValueMapping(source = "FREE", target = "AT_FREE")
    @ValueMapping(source = "PREMIUM", target = "AT_PREMIUM")
    @ValueMapping(source = "ENTERPRISE", target = "AT_ENTERPRISE")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "AT_FREE")
    @ValueMapping(source = MappingConstants.NULL, target = "AT_FREE")
    AccountType toAccountTypeProto(Account.AccountType type);

    @ValueMapping(source = "AT_FREE", target = "FREE")
    @ValueMapping(source = "AT_PREMIUM", target = "PREMIUM")
    @ValueMapping(source = "AT_ENTERPRISE", target = "ENTERPRISE")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "FREE")
    Account.AccountType toAccountTypeEntity(AccountType type);
}
