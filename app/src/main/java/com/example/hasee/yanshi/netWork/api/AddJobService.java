package com.example.hasee.yanshi.netWork.api;


import com.example.hasee.yanshi.pojo.AddJobBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface AddJobService {
    @POST("getJob")
    Observable<List<AddJobBean>> getJob(@Field("contentId") int contentId, @Field("craeteTime") String craeteTime);
}
