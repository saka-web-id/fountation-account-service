package id.web.saka.fountation.util.converter.account.activity;

import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ActivityActionWriteConverter implements org.springframework.core.convert.converter.Converter<id.web.saka.fountation.account.activity.Activity.ActivityAction, String> {
    @Override
    public String convert(id.web.saka.fountation.account.activity.Activity.ActivityAction source) {
        return source.name(); // store enum name in DB
    }
}
