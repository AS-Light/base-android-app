package jx.android.staff.api.model;

public class VersionInfo {
    /**
     * 版本类型1 IOS,2android ,
     */
    private String appType;
    /**
     * 版本ID
     */
    private String id;
    /**
     * 版本CODE
     */
    private String versionCode;
    /**
     * 版本状态1使用，2不可用 ,
     */
    private String versionStatus;
    /**
     * 版本提示文案
     */
    private String versionText;
    /**
     * 版本类型：1：更新不提示，2：更新提示，3：强制更新
     */
    private String versionType;

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(String versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getVersionText() {
        return versionText;
    }

    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
