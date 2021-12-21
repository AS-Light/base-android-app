package jx.android.staff.api.model;

import com.google.gson.Gson;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginInfo implements Serializable {
    public String id;
    public Integer code;
    public String token;

    public static LoginInfo fromJson(String json) {
        return new Gson().fromJson(json, LoginInfo.class);
    }
}
