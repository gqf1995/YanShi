package com.example.hasee.yanshi.pojo.NewPojo;

/**
 * Created by hasee on 2017/8/8.
 */

public class ChangePasswordResult extends BaseResult{
    String message;
    String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
