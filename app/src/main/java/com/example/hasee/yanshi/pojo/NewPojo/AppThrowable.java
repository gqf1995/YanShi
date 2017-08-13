package com.example.hasee.yanshi.pojo.NewPojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hasee on 2017/8/13.
 */

public class AppThrowable extends RealmObject{
    @PrimaryKey
    int throwableId;

    String throwable;
    String phone;
    String time;

    boolean isNew=true;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getThrowableId() {
        return throwableId;
    }

    public void setThrowableId(int throwableId) {
        this.throwableId = throwableId;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AppThrowable{" +
                "throwableId=" + throwableId +
                ", throwable='" + throwable + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
