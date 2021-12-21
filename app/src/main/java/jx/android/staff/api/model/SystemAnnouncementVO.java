package jx.android.staff.api.model;

public class SystemAnnouncementVO {

    /**
     * content : string
     * createTime : 2019-07-25T07:16:52.181Z
     * id : 0
     * isShow : 0
     * status : 0
     * version : string
     */

    private String content;
    private String createTime;
    private String id;
    private String isShow;
    private String status;
    private String version;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
