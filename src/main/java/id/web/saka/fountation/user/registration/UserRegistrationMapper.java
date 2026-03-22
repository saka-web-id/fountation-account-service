package id.web.saka.fountation.user.registration;

import id.web.saka.fountation.account.user.registration.AccountUserRegistrationDTO;
import id.web.saka.fountation.membership.plan.MembershipPlanGrpcMapper;
import id.web.saka.fountation.organization.company.CompanyGrpcMapper;
import id.web.saka.fountation.organization.department.DepartmentGrpcMapper;
import id.web.saka.fountation.user.UserGrpcMapper;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class,
        UserGrpcMapper.class,
        CompanyGrpcMapper.class,
        DepartmentGrpcMapper.class,
        MembershipPlanGrpcMapper.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRegistrationMapper {


    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "account.createdAt", source = "account.createdAt", qualifiedByName = "instantToTimestamp")
    UserRegistrationResponse toProto(AccountUserRegistrationDTO dto);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "account.createdAt", source = "account.createdAt", qualifiedByName = "toInstant")
    AccountUserRegistrationDTO toDTO(UserRegistrationRequest request);

}
