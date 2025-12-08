package id.web.saka.fountation.util.converter.account.activity;

import id.web.saka.fountation.account.activity.Activity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ActivityActionReadConverter implements Converter<String, Activity.ActivityAction> {
    @Override
    public Activity.ActivityAction convert(String source) {
        return Activity.ActivityAction.valueOf(source);
    }
}
