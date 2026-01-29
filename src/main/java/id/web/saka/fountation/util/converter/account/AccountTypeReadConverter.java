package id.web.saka.fountation.util.converter.account;

import id.web.saka.fountation.account.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class AccountTypeReadConverter implements Converter<String, Account.AccountType> {
    @Override
    public Account.AccountType convert(String source) {
        return Account.AccountType.valueOf(source);
    }
}
