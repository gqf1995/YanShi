package com.example.hasee.yanshi.netWork.api;

import com.example.hasee.yanshi.pojo.NewPojo.BaseResult;
import com.example.hasee.yanshi.pojo.NewPojo.ChangePasswordResult;
import com.example.hasee.yanshi.pojo.NewPojo.DetailReportInfo;
import com.example.hasee.yanshi.pojo.NewPojo.InfoNotice;
import com.example.hasee.yanshi.pojo.NewPojo.JonInfo;
import com.example.hasee.yanshi.pojo.NewPojo.LoginResult;
import com.example.hasee.yanshi.pojo.NewPojo.ReportInfo;
import com.example.hasee.yanshi.pojo.addressBook.AddressBook;
import com.example.hasee.yanshi.update.UpdateMsg;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hasee on 2017/7/29.
 */

public interface NewApi {

    //领导工作安排信息
    /*
    请求参数：
departId:6,http://www.jiashengfei.top:8080/municipal/appInfo/JobInfo/7/2017-08-2
craeteTime:2017-07-27，
     */
    @FormUrlEncoded
    @POST("appInfo/JobInfo")//部门ID
    Observable<List<JonInfo>> getJobInfo(@Field("departId") int departId, @Field("craeteTime") String craeteTime);

    //要请汇报
    @FormUrlEncoded
    @POST("appInfo/listReportInfo")//部门ID
    Observable<List<ReportInfo>> getListReportInfo(@Field("departId") int departId);

    //要情汇报详情
    @FormUrlEncoded
    @POST("appInfo/detailReportInfo")//要请ID
    Observable<List<DetailReportInfo>> getDetailReportInfo(@Field("contentId") int contentId);

    //登陆
    @FormUrlEncoded
    @POST("doLoginApp")//要请ID
    Observable<LoginResult> login(@Field("phone_num") String phone_num, @Field("password") String password);

    //通讯录
    @GET("addressBook/getAddressBookDepartmentsBefore")
    Observable<AddressBook> getAddressBook();

    //查看他人工作安排名单
    @GET("addressBook/getOtherLeaderWorkBefore")
    Observable<AddressBook> getOtherLeaderWorkBefore();

    //信息公告
    @GET("appInfo/listInfoNotice")
    Observable<List<InfoNotice>> listInfoNotice(@Query("departId") int departId);

    ///appInfo/updatePasswordByUserId?id=10&password=13465
    //修改密码
    @FormUrlEncoded
    @POST("appInfo/updatePasswordByUserId")//要请ID
    Observable<ChangePasswordResult> updatePasswordByUserId(@Field("id") int id, @Field("password") String password);

    //获取版本信息
    @GET("appInfo/getAppVersion")
    Observable<UpdateMsg> getAppVersion();

    //收集程序异常
    @FormUrlEncoded
    @POST("appInfo/saveAppErr")
    Observable<BaseResult> saveAppErr(@Field("userphone") String userphone, @Field("msg") String msg,@Field("createtime") String createtime);
}
