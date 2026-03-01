package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.account.MembershipPlanName;
import id.web.saka.fountation.account.MembershipPlanProto;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class
})
public interface MembershipPlanGrpcMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToTimestamp")
    @Mapping(target = "features", source = "features", qualifiedByName = "jsonToString")
    MembershipPlanProto toProto(MembershipPlan plan);

    @ValueMapping(source = "FREE", target = "MPN_FREE")
    @ValueMapping(source = "BASIC", target = "MPN_BASIC")
    @ValueMapping(source = "PREMIUM", target = "MPN_PREMIUM")
    @ValueMapping(source = "ENTERPRISE", target = "MPN_ENTERPRISE")
    MembershipPlanName toProto(MembershipPlan.MembershipPlanName name);
}
