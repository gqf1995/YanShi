package com.example.hasee.yanshi.Base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.AppThrowable;
import com.example.hasee.yanshi.pojo.NewPojo.BaseResult;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;
import com.example.hasee.yanshi.utils.NetUtils;
import com.example.hasee.yanshi.utils.PopupUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/8/13.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //文件夹目录
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash_log/";
    //文件名
    private static final String FILE_NAME = "crash";
    //文件名后缀
    private static final String FILE_NAME_SUFFIX = ".trace";
    //上下文
    private Context mContext;

    public static boolean isNetErr=false;
    AppThrowable appThrowable;

    Realm realm;
    //单例模式
    private static CrashHandler sInstance = new CrashHandler();
    private CrashHandler() {}
    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void setAppThrowable(AppThrowable appThrowable) {
        this.appThrowable = appThrowable;
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    public void init(Context context) {
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
        realm=Realm.getDefaultInstance();
    }

    /**
     * 捕获异常回掉
     *
     * @param thread 当前线程
     * @param ex     异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //PopupUtils.showToast(mContext,"抱歉程序出现异常，我们将上传错误并在下一个版本修改，3秒后关闭应用。");
        Log.i("gqf","saveErrToService"+ex.toString());
        dumpExceptionToSDCard(ex);
        //上传异常信息到服务器

        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                PopupUtils.showToast(mContext,"抱歉程序出现异常，我们将上传错误并在下一个版本修改，3秒后关闭应用。");
                if (!NetUtils.isConnected(mContext)) {
                   // saveErr();
                }else{
                    uploadExceptionToServer(appThrowable);
                }
                Looper.loop();
            }
        }).start();

       // EventBus.getDefault().post(appThrowable);
        //延时1秒杀死进程
        SystemClock.sleep(5000);
        subscription.unsubscribe();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 导出异常信息到SD卡
     *
     * @param ex
     */
    private void dumpExceptionToSDCard(Throwable ex) {

        //获取当前时间
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //以当前时间创建log文件

        StringBuffer stringBuffer=new StringBuffer();
        appThrowable=new AppThrowable();

        try {
            //导出手机信息和异常信息
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            stringBuffer.append("time: " + time+"\n");
            stringBuffer.append("Application version: " + pi.versionName+"\n");
            stringBuffer.append("Application version number: " + pi.versionCode+"\n");
            stringBuffer.append("android version number: " + Build.VERSION.RELEASE+"\n");
            stringBuffer.append("android API: " + Build.VERSION.SDK_INT+"\n");
            stringBuffer.append("phone maker:" + Build.MANUFACTURER+"\n");
            stringBuffer.append("phone model: " + Build.MODEL+"\n");
            int lenght= ex.getStackTrace().length;
            for (int i = 0; i < lenght; i++) {
                stringBuffer.append(ex.getStackTrace()[i].toString());
                stringBuffer.append("\n");
            }
            System.out.println(stringBuffer.toString());
        } catch (Exception e) {

        }
        appThrowable.setThrowable(stringBuffer.toString());
        appThrowable.setPhone(realm.where(LoginUser.class).findFirst().getPhone_num());
        appThrowable.setTime(time);
    }

    public void saveErr(){
        if(appThrowable.isNew()&&appThrowable!=null){
            //保存在本地
            appThrowable.setNew(false);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(appThrowable);
            realm.commitTransaction();
        }
    }
    /**
     * 上传异常信息到服务器
     *
     */
    Subscription subscription;
    public void uploadExceptionToServer(AppThrowable appThrowabl) {
        Log.i("gqf","saveErrToService"+appThrowabl.toString());
        if(!isNetErr) {
            isNetErr=true;
            subscription = NetWork.getNewApi().saveAppErr(appThrowabl.getPhone(),
                    appThrowabl.getThrowable(), appThrowabl.getTime()
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("gqf","saveErrToService"+e.toString());
                        }

                        @Override
                        public void onNext(BaseResult baseResult) {
                            Log.i("gqf","saveErrToService"+baseResult.isCode());
//                            if (baseResult.isCode()) {
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                System.exit(0);
//                            } else {
//                                saveErr();
//                            }
                        }
                    });
        }

    }
}