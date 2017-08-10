package com.example.hasee.yanshi.netWork;

import com.example.hasee.yanshi.netWork.api.AddJobService;
import com.example.hasee.yanshi.netWork.api.AddressBookService;
import com.example.hasee.yanshi.netWork.api.LoginService;
import com.example.hasee.yanshi.netWork.api.NewApi;
import com.example.hasee.yanshi.netWork.api.ReportFeelingDetailService;
import com.example.hasee.yanshi.netWork.api.ReportFeelingService;
import com.example.hasee.yanshi.netWork.api.UpdateService;
import com.example.hasee.yanshi.update.DownloadProgressInterceptor;
import com.example.hasee.yanshi.update.DownloadProgressListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {
    private static AddressBookService addressBookService;
    private static LoginService loginService;
    private static ReportFeelingService reportFeelingService;
    private static ReportFeelingDetailService reportFeelingDetailService;
    private static AddJobService addJobService;
    private static NewApi newApi;
    private static UpdateService updateService;

                //创建实例
    public static Retrofit getRetrofit(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        return retrofit;
    }
                //部门
    public static AddressBookService getAddressBookService(){
        if(addressBookService==null){
            Retrofit retrofit=getRetrofit("http://192.168.168.108/municipal/addressBook/getAddressBookDepartmentsBefore/");
            addressBookService=retrofit.create(AddressBookService.class);
        }
      return  addressBookService;
    }
     //登录
    public static LoginService getLoginService(){
        if(loginService==null){
            Retrofit retrofit=getRetrofit("http://192.168.168.108/municipal/doLoginApp/");
            loginService=retrofit.create(LoginService.class);
        }
        return  loginService;
    }

    //要情汇报
    public static ReportFeelingService getReportFeelingService(){
        if(reportFeelingService==null){
            Retrofit retrofit=getRetrofit("http://192.168.168.109/municipal/appInfo/listReportInfo/");
            reportFeelingService=retrofit.create(ReportFeelingService.class);
        }
        return reportFeelingService;
    }

    //要情汇报详情页
    public static ReportFeelingDetailService getReportFeelingDetailService(){
        if(reportFeelingDetailService==null){
            Retrofit retrofit=getRetrofit("http://192.168.168.109/municipal/appInfo/detailReportInfo/");
            reportFeelingDetailService=retrofit.create(ReportFeelingDetailService.class);
        }
        return reportFeelingDetailService;
    }

    //添加工作
    public static AddJobService getAddJobService(){
        if(addJobService==null){
            Retrofit retrofit=getRetrofit(newUrl2+"/");
            addJobService=retrofit.create(AddJobService.class);
        }
        return addJobService;
    }


    public static final String newUrl2= "http://www.jiashengfei.top:8080/municipal";
    public static final String newUrl= "http://1.194.225.66:15968/municipal";
    //http://1.194.225.66/municipal/appInfo/getAppVersion
    public static NewApi getNewApi(){
        if(newApi==null){
            Retrofit retrofit=getRetrofit(newUrl+"/");
            newApi=retrofit.create(NewApi.class);
        }
        return newApi;
    }

    public static UpdateService getUpdateService(String url,DownloadProgressListener listener) {
        if (updateService == null) {
            Retrofit retrofit = getRetrofitDownload(url,listener);
            updateService = retrofit.create(UpdateService.class);
        }
        return updateService;
    }
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static Retrofit getRetrofitDownload(String url,DownloadProgressListener listener) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new DownloadProgressInterceptor(listener))
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }
}
