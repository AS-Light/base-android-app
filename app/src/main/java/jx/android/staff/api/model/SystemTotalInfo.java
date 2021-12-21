package jx.android.staff.api.model;

import java.io.Serializable;

/**
 * @author xfy
 * @description:
 * @date :2019/9/16 15:37
 */
public class SystemTotalInfo implements Serializable {

    /**
     * content : string
     * count : 0
     * lastTime : 2019-09-16T01:17:54.627Z
     */

    private String content;
    private String count;
    private String lastTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
