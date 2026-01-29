package id.web.saka.fountation.account.activity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(value = "activity", schema = "account")
public class Activity {

    public enum ActivityAction { LOGIN, LOGOUT, VIEW_PAGE, UPDATE_PROFILE, CHANGE_PASSWORD }

    @Id
    @Column("id")
    private long id;

    @Column("account_id")
    private long accountId;

    @Column("action")
    private ActivityAction action;

    @Column("uri_path")
    private String uriPath;

    @Column("actor_user_id")
    private long actorUserId;

    @Column("action_at")
    private OffsetDateTime actionAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public ActivityAction getAction() {
        return action;
    }

    public void setAction(ActivityAction action) {
        this.action = action;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public long getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(long actorUserId) {
        this.actorUserId = actorUserId;
    }

    public OffsetDateTime getActionAt() {
        return actionAt;
    }

    public void setActionAt(OffsetDateTime actionAt) {
        this.actionAt = actionAt;
    }
}
