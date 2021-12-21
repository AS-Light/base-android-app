package jx.android.staff.api.model;

/**
 * 腾讯地图获取街景信息
 */
public class TencentMapInfo {
    /**
     * 状态码，0为正常,
     * 310请求参数信息有误，
     * 311Key格式错误,
     * 306请求有护持信息请检查字符串,
     * 110请求来源未被授权
     */
    private String status;
    /**
     * 对status的描述
     */
    private String message;
    /**
     * 详情
     */
    private TencentMapDetail detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TencentMapDetail getDetail() {
        return detail;
    }

    public void setDetail(TencentMapDetail detail) {
        this.detail = detail;
    }
}
