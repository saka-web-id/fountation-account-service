package id.web.saka.fountation.membership.plan;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface MembershipPlanMapper {

    MembershipPlan toEntity(MembershipPlanDTO dto);

    MembershipPlanDTO toDto(MembershipPlan entity);

}
