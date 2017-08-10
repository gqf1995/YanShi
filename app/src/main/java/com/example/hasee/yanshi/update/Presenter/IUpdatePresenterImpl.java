package com.example.hasee.yanshi.update.Presenter;

import android.util.Log;

import com.example.hasee.yanshi.Base.BasePresenterImpl;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.update.DownloadProgress;
import com.example.hasee.yanshi.update.DownloadProgressListener;
import com.example.hasee.yanshi.update.FileUtils;
import com.example.hasee.yanshi.update.UpdateMsg;
import com.example.hasee.yanshi.update.View.IUpdateService;
import com.example.hasee.yanshi.update.exception.CustomizeException;
import com.example.hasee.yanshi.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by johe on 2017/3/27.
 */

public class IUpdatePresenterImpl extends BasePresenterImpl implements IUpdatePresenter {

    IUpdateService iUpdateService;
    File outputFile;
    int downloadCount = 0;

    public IUpdatePresenterImpl(IUpdateService i) {
        super();
        iUpdateService = i;
    }

    public File getOutPutFile() {
        return outputFile;
    }

    public void download(String path, UpdateMsg updateMsg) {
        //下载文件名，地址
        outputFile = new File(path);
        //删除已有
        if (outputFile.exists()) {
            outputFile.delete();
        }
        String baseUrl = StringUtils.getHostName(updateMsg.getApp_url());
        Log.e("gqf", "baseUrl" + baseUrl);
        //开起下载
        Subscription subscription = NetWork.getUpdateService(baseUrl, new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //下载进度监听
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    DownloadProgress download = new DownloadProgress();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);
                    iUpdateService.sendNotification(download);
                }
            }
        })
                .download(updateMsg.getApp_url())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream, outputFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new CustomizeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        iUpdateService.downloadCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //downloadCompleted);
                        iUpdateService.showToast("下载失败");
                    }

                    @Override
                    public void onNext(Object o) {
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void close() {
        super.onClose();
    }
}
