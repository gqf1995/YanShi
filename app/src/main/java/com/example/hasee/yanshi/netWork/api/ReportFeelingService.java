package com.example.hasee.yanshi.netWork.api;


import com.example.hasee.yanshi.pojo.ReportFeeling;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface ReportFeelingService {
    @FormUrlEncoded
    @POST("getRepReportFeeling")
    Observable<List<ReportFeeling>> getRepReportFeeling(@Field("departId") int departId);

}
