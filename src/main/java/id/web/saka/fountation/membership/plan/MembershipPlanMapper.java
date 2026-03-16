package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface MembershipPlanMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toInstant")
    MembershipPlan toEntity(MembershipPlanDTO dto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toOffset")
    MembershipPlanDTO toDto(MembershipPlan entity);

}
