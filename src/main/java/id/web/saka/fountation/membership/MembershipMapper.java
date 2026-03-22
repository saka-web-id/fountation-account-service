package id.web.saka.fountation.membership;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import id.web.saka.fountation.util.mapper.EnumMapper;
import id.web.saka.fountation.util.mapper.JsonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", uses = {
        DateTimeMapper.class,
        EnumMapper.class,
        JsonMapper.class
})
public interface MembershipMapper {


    // --- MembershipStatus Mapping ---

    @ValueMapping(source = "ACTIVE", target = "MS_ACTIVE")
    @ValueMapping(source = "INACTIVE", target = "MS_INACTIVE")
    @ValueMapping(source = "DISABLED", target = "MS_DISABLED")
    @ValueMapping(source = "PENDING", target = "MS_PENDING")
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "MS_INACTIVE")
    @ValueMapping(source = MappingConstants.NULL, target = "MS_INACTIVE")
    MembershipStatus toMembershipStatusProto(Membership.MembershipStatus status);

    @ValueMapping(source = "MS_ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "MS_INACTIVE", target = "INACTIVE")
    @ValueMapping(source = "MS_DISABLED", target = "DISABLED")
    @ValueMapping(source = "MS_PENDING", target = "PENDING")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "INACTIVE")
    Membership.MembershipStatus toMembershipStatusEntity(MembershipStatus status);

}
