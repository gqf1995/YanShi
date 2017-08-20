package com.example.hasee.yanshi.Job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.yanshi.Adapter.AutoHeightLayoutManager;
import com.example.hasee.yanshi.Adapter.JobListAdapter;
import com.example.hasee.yanshi.Adapter.ReportInfoListAdapter;
import com.example.hasee.yanshi.Base.BaseFragment;
import com.example.hasee.yanshi.ChangePasswordActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.Report.ReportDetailActivity;
import com.example.hasee.yanshi.dialog.MsgDialog;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.JonInfo;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;
import com.example.hasee.yanshi.pojo.NewPojo.ReportInfo;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/28.
 */

public class JobFragment extends BaseFragment {

    LoginUser loginUser;

    Unbinder unbinder;
    @BindView(R.id.person_Image2)
    ImageView personImage2;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.button)
    Button button;
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
    @BindView(R.id.user_name_txt)
    TextView userNameTxt;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.user_position_txt)
    TextView userPositionTxt;
    @BindView(R.id.find_other_lin)
    LinearLayout findOtherLin;
    @BindView(R.id.contact_lin)
    LinearLayout contactLin;
    MsgDialog msgDialog;
    @BindView(R.id.job_Title_TextView)
    TextView jobTitleTextView;
    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.change_lin)
    LinearLayout changeLin;
    @BindView(R.id.load_more_list_view_ptr_frame)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.root_scroll)
    NestedScrollView rootScroll;

    public static JobFragment newInstance(String param1) {
        Bundle args = new Bundle();
        JobFragment fragment = new JobFragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public JobFragment() {
    }

    @OnClick({R.id.last_Day_Button, R.id.next_Day_Button, R.id.find_other_lin, R.id.change_lin, R.id.line2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.last_Day_Button:
                timeDisplayTextView.setText(getTime(-1));
                // int i=100/0;
                break;
            case R.id.next_Day_Button:
                timeDisplayTextView.setText(getTime(1));

                break;
            case R.id.find_other_lin:
                mListener.startNewActivity(new Intent(getActivity(), OtherJobTreeActivity.class));

                break;
            case R.id.change_lin:
                mListener.startNewActivity(new Intent(getActivity(), ChangePasswordActivity.class));

                break;
            case R.id.line2:
                msgDialog = new MsgDialog(getActivity(), R.style.dialog, "分工：" + loginUser.getUser_division(), new MsgDialog.OnCloseListener() {
                    @Override
                    public void ok() {
                        msgDialog.dismiss();
                        msgDialog = null;
                    }
                });
                msgDialog.showDialog();
                break;

        }
    }


    public interface mListener {
        public void startNewActivity(Intent intent);
    }

    private mListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_job, container, false);
        loginUser = realm.where(LoginUser.class).findFirst();
        Log.i("gqf", "loginUser" + loginUser.toString());
        unbinder = ButterKnife.bind(this, view);
        getReportData(loginUser.getDepartment_id());
        initView();
        getThreeDayJob();
        initRefresh();
        return view;
    }

    public void initRefresh() {
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rootScroll, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //实现下拉刷新的功能
                Log.i("test", "-----onRefreshBegin-----");
                mPtrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getReportData(loginUser.getDepartment_id());
                        getThreeDayJob();
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getReportData(loginUser.getDepartment_id());
            getThreeDayJob();
        }
    }

    public void initView() {
        timeDisplayTextView.setText(getTime(0));
        jobTitleTextView.setText("分工：" + loginUser.getUser_division());
        userPositionTxt.setText("    " + loginUser.getUser_position());
        userNameTxt.setText(loginUser.getName());
        Picasso.with(getContext()).load(NetWork.newUrl + loginUser.getImage()).into(personImage2);
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
                        mPtrFrameLayout.refreshComplete();
                    }

                    @Override
                    public void onNext(List<ReportInfo> reportInfos) {
                        mPtrFrameLayout.refreshComplete();
                        Log.i("gqf", "getListReportInfo" + reportInfos.toString());
                        initReportList(reportInfos);
                    }
                });
        compositeSubscription.add(subscription);
    }

    ReportInfoListAdapter reportInfoListAdapter;

    public void initReportList(List<ReportInfo> reportInfos) {
        if (reportInfoListAdapter == null) {
            reportInfoListAdapter = new ReportInfoListAdapter(getActivity(), reportInfos);
            ReportRecyclerview.setLayoutManager(new AutoHeightLayoutManager(getActivity()));
            ReportRecyclerview.setAdapter(reportInfoListAdapter);
            reportInfoListAdapter.setOnItemClickListener(new ReportInfoListAdapter.MyItemClickListener() {
                @Override
                public void OnClickListener(int position) {
                    //跳转详情页面
                    Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                    intent.putExtra(ReportDetailActivity.CONTENT_ID, reportInfoListAdapter.getDataItem(position).getId());
                    mListener.startNewActivity(intent);
                }
            });
            ReportRecyclerview.setNestedScrollingEnabled(false);
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
            jobListAdapter = new JobListAdapter(getActivity(), JobInfo);
            jobRecycerView.setLayoutManager(new AutoHeightLayoutManager(getActivity()));
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
        getJobData(-1, loginUser.getDepartment_id(), df.format(new Date(d.getTime() + -1 * 24 * 60 * 60 * 1000)));
        getJobData(0, loginUser.getDepartment_id(), df.format(new Date(d.getTime() + 0 * 24 * 60 * 60 * 1000)));
        getJobData(1, loginUser.getDepartment_id(), df.format(new Date(d.getTime() + 1 * 24 * 60 * 60 * 1000)));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
