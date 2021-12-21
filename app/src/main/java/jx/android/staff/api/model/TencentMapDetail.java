package jx.android.staff.api.model;

/**
 * 街景信息详情
 */
public class TencentMapDetail {
    /**
     * 场景唯一标识
     */
    private String id;
    /**
     * 场景对应坐标
     */
//    private String location;
    /**
     * 场景点描述信息，如当前道路等。
     */
    private String description;
    /**
     * 查看器视线与正北方的水平夹角，以度为单位。
     */
    private String heading;
    /**
     * 查看器视线与地面的夹角, 以度为单位。
     */
    private String pitch;
    /**
     * 放大级别，值大于1小于4。
     */
    private String zoom;
    /**
     * 视角体验，取值：
     * 4：人工校验过的视角，可以很好地看到指定地点（使用POI参数获取街景时才会有可能有此值）
     * 2：通过坐标自动获取最近街景并计算得到的heading，视角效果存在不确定性
     * 1,3,5：预留
     */
    private String pov_exp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getPov_exp() {
        return pov_exp;
    }

    public void setPov_exp(String pov_exp) {
        this.pov_exp = pov_exp;
    }
}
