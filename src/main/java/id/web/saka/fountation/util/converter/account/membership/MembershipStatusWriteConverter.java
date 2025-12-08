package id.web.saka.fountation.util.converter.account.membership;

import id.web.saka.fountation.account.membership.Membership;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MembershipStatusWriteConverter implements Converter<Membership.MembershipStatus, String> {

    @Override
    public String convert(Membership.MembershipStatus source) {
        return source.name(); // store enum name in DB
    }

}
