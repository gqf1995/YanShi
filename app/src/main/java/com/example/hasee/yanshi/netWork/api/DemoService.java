package com.example.hasee.yanshi.netWork.api;

import com.example.hasee.yanshi.pojo.NewPojo.BaseResult;
import com.example.hasee.yanshi.pojo.NewPojo.ContentObj;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hasee on 2017/8/13.
 */

public interface DemoService {
    //收集程序异常
    @FormUrlEncoded
    @POST("appInfo/saveAppErr")
    Observable<BaseResult> saveAppErr(@Field("userPhone") String userPhone, @Field("msg") String msg, @Field("createtime") String createtime);


    //查看他人工作安排名单
    @GET("addressBook/getOtherLeaderWorkBefore")
    Observable<ContentObj> getOtherLeaderWorkBefore();

    //通讯录
    @GET("addressBook/getAddressBookDepartmentsBefore")
    Observable<ContentObj> getAddressBook();

}
