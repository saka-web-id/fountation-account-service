package id.web.saka.fountation.util.converter.account;

import id.web.saka.fountation.account.Account;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class AccountStatusReadConverter implements Converter<String, Account.AccountStatus> {
    @Override
    public Account.AccountStatus convert(String source) {
        return Account.AccountStatus.valueOf(source);
    }
}

