package jx.android.staff.api.model;

import com.google.gson.Gson;

public class StatusStringInfo {

    public String status;
    public String data;

    public static StatusStringInfo fromJson(String json) {
        return new Gson().fromJson(json, StatusStringInfo.class);
    }
}
