package id.web.saka.fountation.account.setting;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AccountSettingService {
    Logger logger = org.slf4j.LoggerFactory.getLogger(AccountSettingService.class);

    private final AccountSettingRepository accountSettingRepository;

    public AccountSettingService(AccountSettingRepository accountSettingRepository) {
        this.accountSettingRepository = accountSettingRepository;
    }
}
