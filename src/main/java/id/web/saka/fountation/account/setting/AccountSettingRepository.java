package id.web.saka.fountation.account.setting;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingRepository extends org.springframework.data.repository.reactive.ReactiveCrudRepository<AccountSetting, Long> {
}
