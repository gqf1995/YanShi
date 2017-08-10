package com.example.hasee.yanshi.update;

/**
 * Created by johe on 2016/12/29.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.example.hasee.yanshi.MainActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.update.Presenter.IUpdatePresenter;
import com.example.hasee.yanshi.update.Presenter.IUpdatePresenterImpl;
import com.example.hasee.yanshi.update.View.IUpdateService;

import java.io.File;

/**
 * 不要忘记注册，在mainfest文件中
 */
public class UpdateService extends Service implements IUpdateService{
    //文件存储
    private File updateDir = null;

    IUpdatePresenter iUpdatePresenter;

    public static boolean isRun=false;

    //通知栏

    //通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        iUpdatePresenter=new IUpdatePresenterImpl(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iUpdatePresenter.close();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取传值
        Log.i("gqf","onStartCommand");
        if(intent.getStringExtra("getUpdateContent")!=null) {
            isRun = true;
            UpdateMsg updateMsg = new UpdateMsg();
            updateMsg.setUpdateContent(intent.getStringExtra("getUpdateContent"));
            updateMsg.setApp_version(intent.getStringExtra("setApp_version"));
            updateMsg.setApp_url(intent.getStringExtra("getApp_url"));
            Log.i("gqf", "onStartCommand" + updateMsg.toString());
            //创建下载后保存文件夹
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                //文件夹路径
                updateDir = new File(Environment.getExternalStorageDirectory(), UpdateInformation.downloadDir);
            }
            //没有则创建文件夹
            if (!updateDir.exists()) {
                updateDir.mkdir();
            }
            //设置下载过程中，点击通知栏，回到主界面
            updateIntent = new Intent(this, MainActivity.class);
            updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
            //下载通知
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(this.getResources().getString(R.string.app_name) + "正在更新")
                    .setContentText(updateMsg.getUpdateContent())
                    .setContentIntent(updatePendingIntent)
                    .setAutoCancel(true);
            notificationManager.notify(0, notificationBuilder.build());
            Log.i("gqf", "开始下载");
            //Log.i("gqf","path"+updateDir.getAbsolutePath()+"/"+getResources().getString(R.string.app_name)+".apk");
            iUpdatePresenter.download(updateDir.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + ".apk", updateMsg);
            //downloadCompleted();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    //通知栏显示下载
    public void sendNotification(DownloadProgress download) {
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
               StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtils.getDataSize(download.getTotalFileSize()));
        notificationBuilder.setContentIntent(updatePendingIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }
    //下载完成
    public void downloadCompleted() {
        //完成下载
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText(this.getResources().getString(R.string.app_name)+"更新完成");
        //完成后直接跳转安装界面，安装apk
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//        Uri URI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", iUpdatePresenter.getOutPutFile());
//       // intent.setDataAndType(Uri.fromFile(iUpdatePresenter.getOutPutFile()), "application/vnd.android.package-archive");
//        intent.setDataAndType(URI, "application/vnd.android.package-archive");
//        startActivity(intent);


        Intent install;
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(this, "com.example.hasee.yanshi.provider", iUpdatePresenter.getOutPutFile());//在AndroidManifest中的android:authorities值
             install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else {
            install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(iUpdatePresenter.getOutPutFile()), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }




        //也可通过点击通知跳转安装界面
        notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, install, 0));
        notificationManager.notify(0, notificationBuilder.build());
    }
    //提示
    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}

