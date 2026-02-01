package id.web.saka.fountation.membership.billing.cycle;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface BillingCycleMapper {

    BillingCycle toEntity(BillingCycleDTO dto);

    BillingCycleDTO toDto(BillingCycle entity);

}
