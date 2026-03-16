package id.web.saka.fountation.membership.billing.cycle;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface BillingCycleMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toInstant")
    BillingCycle toEntity(BillingCycleDTO dto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toOffset")
    BillingCycleDTO toDto(BillingCycle entity);

}
