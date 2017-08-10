package com.example.hasee.yanshi;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.hasee.yanshi.Base.BaseApplication;
import com.example.hasee.yanshi.Contact.ContactDialogFragment;
import com.example.hasee.yanshi.Contact.ContactFragment;
import com.example.hasee.yanshi.Job.JobFragment;
import com.example.hasee.yanshi.Msg.MsgFragment;
import com.example.hasee.yanshi.Report.ReportFragment;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.example.hasee.yanshi.update.UpdateInformation;
import com.example.hasee.yanshi.update.UpdateMsg;
import com.example.hasee.yanshi.update.UpdateService;
import com.example.hasee.yanshi.utils.NetUtils;
import com.example.hasee.yanshi.utils.PopupUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.hasee.yanshi.R.drawable.job;


public class MainActivity extends AppCompatActivity implements JobFragment.mListener,ReportFragment.mListener,MsgFragment.mListener{

    private static final String JOB_TAG = "JOB_TAG";
    private static final String REPORT_TAG = "REPORT_TAG";
    private static final String MSG_TAG = "MSG_TAG";
    private static final String CONTACT_TAG = "CONTACT_TAG";
    private static final int CONTENT_JOB = 0;
    private static final int CONTENT_REPORT = 1;
    private static final int CONTENT_MSG= 2;
    private static final int CONTENT_CONTACT = 3;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1234;
    @BindView(R.id.toolbar_Text)
    TextView toolbarText;
    @BindView(R.id.friend_button)
    Button friendButton;
    @BindView(R.id.container_Framelayout)
    FrameLayout containerFramelayout;
    @BindView(R.id.bottomBar)
    BottomNavigationBar bottomBar;
    String toolBarTxt;

    JobFragment jobFragment;
    ReportFragment reportFragment;
    MsgFragment msgFragment;
    ContactFragment contactFragment;
    CompositeSubscription compositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initFragemnt();
        EventBus.getDefault().register(this);
        compositeSubscription=new CompositeSubscription();
        initUpdata();
    }

    public void initFragemnt(){
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(job, "工作安排").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.infor_gonggao, "要情汇报").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.anpai, "信息公告").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.tongxun, "通讯录").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case CONTENT_JOB:
                        setContent(CONTENT_JOB);
                        break;
                    case CONTENT_REPORT:
                        setContent(CONTENT_REPORT);
                        break;
                    case CONTENT_MSG:
                        setContent(CONTENT_MSG);
                        break;
                    case CONTENT_CONTACT:
                        setContent(CONTENT_CONTACT);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        setContent(CONTENT_JOB);
    }
    public void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_JOB:
                 String job = getResources().getString(R.string.job);
                toolBarTxt=job;
                jobFragment = (JobFragment) getSupportFragmentManager().findFragmentByTag(JOB_TAG);
                hideFragment(JOB_TAG);
                if (jobFragment == null) {
                    jobFragment = JobFragment.newInstance(job);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container_Framelayout, jobFragment, JOB_TAG).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(jobFragment).commitNow();
                }
                break;
            case CONTENT_REPORT:
                String report = getResources().getString(R.string.report);
                toolBarTxt=report;
                reportFragment = (ReportFragment) getSupportFragmentManager().findFragmentByTag(REPORT_TAG);
                hideFragment(REPORT_TAG);
                if (reportFragment == null) {
                    reportFragment = ReportFragment.newInstance(report);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container_Framelayout, reportFragment, REPORT_TAG).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(reportFragment).commitNow();
                }

                break;
            case CONTENT_MSG:
                String msg = getResources().getString(R.string.msg);
                toolBarTxt=msg;
                msgFragment = (MsgFragment) getSupportFragmentManager().findFragmentByTag(MSG_TAG);
                hideFragment(MSG_TAG);
                if (msgFragment == null) {
                    msgFragment = MsgFragment.newInstance(msg);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container_Framelayout, msgFragment, MSG_TAG).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(msgFragment).commitNow();
                }
                break;
            case CONTENT_CONTACT:
                String contact = getResources().getString(R.string.contact);
                toolBarTxt=contact;
                contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag(CONTACT_TAG);
                hideFragment(CONTACT_TAG);
                if (contactFragment == null) {
                    contactFragment = ContactFragment.newInstance(contact);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container_Framelayout, contactFragment, CONTACT_TAG).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(contactFragment).commitNow();
                }
                break;
        }

    }

    private void hideFragment(String tag) {
        if (jobFragment != null && tag != JOB_TAG) {
            getSupportFragmentManager().beginTransaction().hide(jobFragment).commitNow();
        }
        if (reportFragment != null && tag != REPORT_TAG) {
            getSupportFragmentManager().beginTransaction().hide(reportFragment).commitNow();
        }
        if (msgFragment != null && tag != MSG_TAG) {
            getSupportFragmentManager().beginTransaction().hide(msgFragment).commitNow();
        }
        if (contactFragment != null && tag != CONTACT_TAG) {
            getSupportFragmentManager().beginTransaction().hide(contactFragment).commitNow();
        }
        toolbarText.setText(toolBarTxt);
    }
    ContactDialogFragment contactDialogFragment;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showContactDialog(ContactEvent contactEvent){

        contactDialogFragment = new ContactDialogFragment();
        contactDialogFragment.setContactEvent(contactEvent);
        contactDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Transparent);
        contactDialogFragment.show(getSupportFragmentManager(), "LoginRegisteredDialogFragment");
    }
    AlertDialog.Builder alert;
    UpdateMsg mUpdateMsg;
    public void initUpdata(){
        if (!NetUtils.isConnected(this)) {
            Toast.makeText(this, "网络尚未连接", Toast.LENGTH_LONG).show();
        } else {
            Subscription subscription = NetWork.getAddJobService().getAppVersion()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UpdateMsg>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("gqf",e.toString());
                        }

                        @Override
                        public void onNext(UpdateMsg updateMsg) {
                            mUpdateMsg=updateMsg;
                            if(!UpdateService.isRun) {
                                if (BaseApplication.isUpdateForVersion(updateMsg.getApp_version(), UpdateInformation.localVersion)) {
                                    Log.i("gqf", UpdateInformation.localVersion + "updateMsg" + updateMsg.toString());
                                    if (alert == null) {
                                        alert = new AlertDialog.Builder(MainActivity.this);
                                    }
                                    alert.setTitle("软件升级")
                                            .setMessage(updateMsg.getUpdateContent())
                                            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //开启更新服务UpdateService
                                                    //这里为了把update更好模块化，可以传一些updateService依赖的值
                                                    //如布局ID，资源ID，动态获取的标题,这里以app_name为例
                                                    if(Build.VERSION.SDK_INT>=23) {//判读版本是否在6.0以上
                                                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                != PackageManager.PERMISSION_GRANTED) {
                                                            //申请WRITE_EXTERNAL_STORAGE权限
                                                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                                                        } else {
                                                            PopupUtils.showToast(MainActivity.this, "开始更新，可在通知栏查看进度");
                                                            startUpdateService(mUpdateMsg);
                                                        }
                                                    }else{
                                                        PopupUtils.showToast(MainActivity.this, "开始更新，可在通知栏查看进度");
                                                        startUpdateService(mUpdateMsg);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alert.create().show();
                                }
                            }
                        }
                    });
            compositeSubscription.add(subscription);
        }
    }
    //开起后台更新服务
    public void startUpdateService(UpdateMsg updateMsg) {
        Intent updateIntent = new Intent(MainActivity.this, UpdateService.class);
        updateIntent.putExtra("getUpdateContent", "偃师党政办公平台"+ updateMsg.getApp_version());
        updateIntent.putExtra("getApp_version", updateMsg.getApp_version());
        updateIntent.putExtra("getApp_url", updateMsg.getApp_url());
        startService(updateIntent);
    }
    public void startNewActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        compositeSubscription.unsubscribe();
    }
}
