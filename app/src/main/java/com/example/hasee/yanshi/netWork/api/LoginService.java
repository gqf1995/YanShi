package com.example.hasee.yanshi.netWork.api;


import com.example.hasee.yanshi.pojo.Login.False;
import com.example.hasee.yanshi.pojo.Login.NotDepartment;
import com.example.hasee.yanshi.pojo.Login.Successful;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface LoginService {
    @GET("getFalse")
        Observable<False> getFalse();
    @GET("getNotDepartment")
    Observable<NotDepartment> getNotDepartment();
    @GET("getSuccessful")
    Observable<Successful> getSuccessful();
    @GET("getUserBean")
    Observable<Successful.UserBean> getUserBean(@Path("phone_num") String phone_num, @Path("password") String password);
}
