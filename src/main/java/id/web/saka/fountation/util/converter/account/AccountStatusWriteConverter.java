package id.web.saka.fountation.util.converter.account;

import id.web.saka.fountation.account.Account;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class AccountStatusWriteConverter implements Converter<Account.AccountStatus, String> {
    @Override
    public String convert(Account.AccountStatus source) {
        return source.name(); // store enum name in DB
    }
}

