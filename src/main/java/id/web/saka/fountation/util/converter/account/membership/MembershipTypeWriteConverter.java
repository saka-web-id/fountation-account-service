package id.web.saka.fountation.util.converter.account.membership;

import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MembershipTypeWriteConverter implements org.springframework.core.convert.converter.Converter<id.web.saka.fountation.account.membership.Membership.MembershipType, String> {

    @Override
    public String convert(id.web.saka.fountation.account.membership.Membership.MembershipType source) {
        return source.name();
    }
}
