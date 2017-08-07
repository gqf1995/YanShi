package com.example.hasee.yanshi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.hasee.yanshi.Contact.ContactDialogFragment;
import com.example.hasee.yanshi.Contact.ContactFragment;
import com.example.hasee.yanshi.Job.JobFragment;
import com.example.hasee.yanshi.Msg.MsgFragment;
import com.example.hasee.yanshi.Report.ReportFragment;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initFragemnt();
        EventBus.getDefault().register(this);
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


    public void startNewActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
