package id.web.saka.fountation.util.converter.account.membership;

import id.web.saka.fountation.membership.Membership;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MembershipStatusReadConverter implements Converter<String, Membership.MembershipStatus> {
    @Override
    public Membership.MembershipStatus convert(String source) {
        return Membership.MembershipStatus.valueOf(source); // convert string from DB to enum
    }
}
