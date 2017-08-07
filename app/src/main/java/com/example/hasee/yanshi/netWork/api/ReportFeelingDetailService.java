package com.example.hasee.yanshi.netWork.api;


import com.example.hasee.yanshi.pojo.ReportFeelingDetail;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface ReportFeelingDetailService {
    @FormUrlEncoded
    @POST("getRepReportFeelingDetail")
    Observable<ReportFeelingDetail> getRepReportFeelingDetail(@Field("contentId") int contentId);
}
