package com.example.hasee.yanshi.Job;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.yanshi.Adapter.AutoHeightLayoutManager;
import com.example.hasee.yanshi.Adapter.JobListAdapter;
import com.example.hasee.yanshi.Adapter.ReportInfoListAdapter;
import com.example.hasee.yanshi.Base.BaseActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.Report.ReportDetailActivity;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.example.hasee.yanshi.pojo.NewPojo.JonInfo;
import com.example.hasee.yanshi.pojo.NewPojo.ReportInfo;
import com.example.hasee.yanshi.pojo.addressBook.SysUserBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/30.
 */

public class GetOtherJobActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.person_Image2)
    ImageView personImage2;
    @BindView(R.id.user_name_txt)
    TextView userNameTxt;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.job_Title_TextView)
    TextView jobTitleTextView;
    @BindView(R.id.find_other_lin)
    LinearLayout findOtherLin;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.contact_lin)
    LinearLayout contactLin;
    @BindView(R.id.last_Day_Button)
    ImageButton lastDayButton;
    @BindView(R.id.time_Display_TextView)
    TextView timeDisplayTextView;
    @BindView(R.id.next_Day_Button)
    ImageButton nextDayButton;
    @BindView(R.id.job_Content)
    TextView jobContent;
    @BindView(R.id.job_RecycerView)
    RecyclerView jobRecycerView;
    @BindView(R.id.Report_Recyclerview)
    RecyclerView ReportRecyclerview;

    ContactEvent contactEvent;
    Gson gson;
    SysUserBean loginUser;
    @BindView(R.id.user_position_txt)
    TextView userPositionTxt;
    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.change_lin)
    LinearLayout changeLin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_other_job);
        ButterKnife.bind(this);
        gson = new Gson();
        String json = getIntent().getStringExtra("ContactEvent");
        contactEvent = gson.fromJson(json, ContactEvent.class);
        loginUser = contactEvent.getSysUser();
        toolbar.setTitle(loginUser.getName());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initView();
        getReportData(loginUser.getDepartment_id());
        getThreeDayJob();
    }

    @OnClick({R.id.last_Day_Button, R.id.next_Day_Button, R.id.contact_lin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.last_Day_Button:
                timeDisplayTextView.setText(getTime(-1));
                break;
            case R.id.next_Day_Button:
                timeDisplayTextView.setText(getTime(1));
                break;
            case R.id.contact_lin:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactEvent.getSysUser().getPhone_num()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    public void initView() {
        timeDisplayTextView.setText(getTime(0));
        jobTitleTextView.setText("分工：" + loginUser.getUser_division());
        userPositionTxt.setText("    "+loginUser.getUser_position());
        userNameTxt.setText(loginUser.getName());
        findOtherLin.setVisibility(View.GONE);
        changeLin.setVisibility(View.GONE);
        contactLin.setVisibility(View.VISIBLE);
        Picasso.with(this).load(NetWork.newUrl + loginUser.getImage()).into(personImage2);
    }

    public void getReportData(int id) {
        Subscription subscription = NetWork.getNewApi().getListReportInfo(id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<List<ReportInfo>, Observable<ReportInfo>>() {
                    @Override
                    public Observable<ReportInfo> call(List<ReportInfo> seats) {
                        return Observable.from(seats.subList(0, 5));
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReportInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "getListReportInfo" + e.toString());
                    }

                    @Override
                    public void onNext(List<ReportInfo> reportInfos) {
                        Log.i("gqf", "getListReportInfo" + reportInfos.toString());
                        initReportList(reportInfos);
                    }
                });
        compositeSubscription.add(subscription);
    }

    ReportInfoListAdapter reportInfoListAdapter;

    public void initReportList(List<ReportInfo> reportInfos) {
        if (reportInfoListAdapter == null) {
            reportInfoListAdapter = new ReportInfoListAdapter(this, reportInfos);
            ReportRecyclerview.setLayoutManager(new AutoHeightLayoutManager(this));
            ReportRecyclerview.setAdapter(reportInfoListAdapter);
            reportInfoListAdapter.setOnItemClickListener(new ReportInfoListAdapter.MyItemClickListener() {
                @Override
                public void OnClickListener(int position) {
                    //跳转详情页面
                    Intent intent = new Intent(GetOtherJobActivity.this, ReportDetailActivity.class);
                    intent.putExtra(ReportDetailActivity.CONTENT_ID, reportInfoListAdapter.getDataItem(position).getId());
                    startActivity(intent);
                }
            });
        } else {
            reportInfoListAdapter.update(reportInfos);
        }
    }

    public void getJobData(final int index, int id, String craeteTime) {
        Subscription subscription = NetWork.getNewApi().getJobInfo(id, craeteTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<JonInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<JonInfo> JobInfo) {
                        Log.i("gqf", "reportInfos" + JobInfo.toString());
                        switch (index) {
                            case 0:
                                JobInfoToday = JobInfo;
                                initJobList(JobInfoToday);
                                break;
                            case 1:
                                JobInfoTomorrow = JobInfo;
                                break;
                            case -1:
                                JobInfoYesterday = JobInfo;
                                break;
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    JobListAdapter jobListAdapter;

    public void initJobList(List<JonInfo> JobInfo) {
        if (jobListAdapter == null) {
            jobListAdapter = new JobListAdapter(this, JobInfo);
            jobRecycerView.setLayoutManager(new AutoHeightLayoutManager(this));
            jobRecycerView.setAdapter(jobListAdapter);
            jobRecycerView.setVisibility(View.VISIBLE);
        } else {
            jobListAdapter.update(JobInfo);
        }
    }

    List<JonInfo> JobInfoToday;
    List<JonInfo> JobInfoYesterday;
    List<JonInfo> JobInfoTomorrow;
    String dataStr = "今天";
    int dataIndex = 0;

    public void getThreeDayJob() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        getJobData(-1, loginUser.getId(), df.format(new Date(d.getTime() + -1 * 24 * 60 * 60 * 1000)));
        getJobData(0, loginUser.getId(), df.format(new Date(d.getTime() + 0 * 24 * 60 * 60 * 1000)));
        getJobData(1, loginUser.getId(), df.format(new Date(d.getTime() + 1 * 24 * 60 * 60 * 1000)));
    }

    public String getTime(int num) {
        if (dataIndex == 0 || dataIndex == 1 || dataIndex == -1) {
            if (num > 0 && dataIndex != 1) {
                dataIndex++;
            } else if (num < 0 && dataIndex != -1) {
                dataIndex--;
            } else {
                return dataStr;
            }
            switch (dataIndex) {
                case 0:
                    dataStr = "今天";
                    initJobList(JobInfoToday);
                    break;
                case 1:
                    dataStr = "明天";
                    initJobList(JobInfoTomorrow);
                    break;
                case -1:
                    dataStr = "昨天";
                    initJobList(JobInfoYesterday);
                    break;
            }
        }
        return dataStr;
    }


}
