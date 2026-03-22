package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MembershipPlanGrpcMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToTimestamp")
    @Mapping(target = "features", source = "features", qualifiedByName = "jsonToString")
    @Mapping(target = "name", source = "name", defaultValue = "MPN_FREE")
    MembershipPlanProto toProto(MembershipPlan plan);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toProtoTimestamp")
    @Mapping(target = "features", source = "features", qualifiedByName = "jsonToString")
    @Mapping(target = "name", source = "name", defaultValue = "MPN_FREE")
    MembershipPlanProto toProto(MembershipPlanDTO plan);

    @ValueMapping(source = "FREE", target = "MPN_FREE")
    @ValueMapping(source = "BASIC", target = "MPN_BASIC")
    @ValueMapping(source = "PREMIUM", target = "MPN_PREMIUM")
    @ValueMapping(source = "ENTERPRISE", target = "MPN_ENTERPRISE")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "MPN_FREE")
    @ValueMapping(source = MappingConstants.NULL, target = "MPN_FREE")
    MembershipPlanName toProto(MembershipPlan.MembershipPlanName name);
}
