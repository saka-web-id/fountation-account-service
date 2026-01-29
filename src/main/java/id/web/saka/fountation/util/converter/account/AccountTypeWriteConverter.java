package id.web.saka.fountation.util.converter.account;

import id.web.saka.fountation.account.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class AccountTypeWriteConverter implements Converter<Account.AccountType, String> {
    @Override
    public String convert(Account.AccountType source) {
        return source.name();
    }

}
