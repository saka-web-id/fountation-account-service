package id.web.saka.fountation.account;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface AccountMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toOffset")
    Account toEntity(AccountDTO dto);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toInstant")
    AccountDTO toDTO(Account entity);

}
