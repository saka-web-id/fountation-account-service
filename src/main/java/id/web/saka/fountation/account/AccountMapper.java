package id.web.saka.fountation.account;

import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface AccountMapper {

    Account toEntity(AccountDTO dto);

    AccountDTO toDTO(Account entity);

}
