package id.web.saka.fountation.account.membership;

import id.web.saka.fountation.account.Account;
import id.web.saka.fountation.membership.Membership;
import id.web.saka.fountation.util.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DateTimeMapper.class })
public interface AccountMembershipMapper {

    @Mapping(source = "account.number", target = "accountNumber")
    @Mapping(source = "account.status", target = "accountStatus")
    @Mapping(source = "account.type", target = "accountType")
    @Mapping(source = "account.createdAt", target = "accountCreatedAt")
    @Mapping(source = "membership.planId", target = "membershipPlanId")
    @Mapping(source = "membership.status", target = "membershipStatus")
    @Mapping(source = "membership.startDate", target = "membershipStartDate")
    @Mapping(source = "membership.endDate", target = "membershipEndDate")
    AccountMembershipDTO toDto(Account account, Membership membership);

}
