package id.web.saka.fountation.account.activity;

import java.time.OffsetDateTime;

public class Activity {

    public enum ActivityAction { LOGIN, LOGOUT, VIEW_PAGE, UPDATE_PROFILE, CHANGE_PASSWORD }

    private long id;

    private long accountId;

    private ActivityAction action;

    private String pageUrlPath;

    private long actorUserId;

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

    public String getPageUrlPath() {
        return pageUrlPath;
    }

    public void setPageUrlPath(String pageUrlPath) {
        this.pageUrlPath = pageUrlPath;
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
