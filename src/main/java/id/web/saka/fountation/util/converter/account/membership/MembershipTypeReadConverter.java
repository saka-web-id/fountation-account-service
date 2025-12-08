package id.web.saka.fountation.util.converter.account.membership;

import id.web.saka.fountation.account.membership.Membership;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MembershipTypeReadConverter implements Converter<String, Membership.MembershipType> {
    @Override
    public Membership.MembershipType convert(String source) {
        return Membership.MembershipType.valueOf(source);
    }
}
