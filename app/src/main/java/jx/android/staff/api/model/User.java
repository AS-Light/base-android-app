package jx.android.staff.api.model;

import com.google.gson.Gson;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户信息表
 */
@Data
public class User implements Serializable {
    private String id;           // ID
    private String username;
    private String nickname;
    private String salt;
    private String email;
    private String mobile;
    private Integer status;

    public static User fromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
