package com.example.hasee.yanshi.pojo.NewPojo;

/**
 * Created by hasee on 2017/7/29.
 */

public class LoginResult {
    /**
     * msg : 对不起,您不是部门领导
     * code : false
     */

    private String msg;
    private boolean code;
    LoginUser user;

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
}
