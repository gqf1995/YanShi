package com.example.hasee.yanshi.add_job;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.AddJobBean;
import com.example.hasee.yanshi.pojo.ReportFeeling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/*
*lzz
*/
public class Add_job_fragment extends Fragment {
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    List<String> mData_of_job;
    List<ReportFeeling> mData_Of_Report;
    RecyclerView recyclerView_of_job;
    RecyclerView Report_Recyclerview;
    Adapter_Report adapter_report;
    TextView timeDisplayTextView;
    TextView job_Content;
    Button last_Day_Button;
    Calendar calendar;
    int day=1;
    Button next_Day_Button;


    public static Add_job_fragment newInstance(String param1) {
        Bundle args = new Bundle();
        Add_job_fragment fragment = new Add_job_fragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Add_job_fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_job,container,false);
        //获取控件
         recyclerView_of_job =(RecyclerView)view.findViewById(R.id.job_RecycerView);
         Report_Recyclerview=(RecyclerView)view.findViewById(R.id.Report_Recyclerview);
         timeDisplayTextView=(TextView)view.findViewById(R.id.time_Display_TextView);
        job_Content=(TextView)view.findViewById(R.id.job_Content);
         last_Day_Button=(Button)view.findViewById(R.id.last_Day_Button);
         next_Day_Button=(Button)view.findViewById(R.id.next_Day_Button);
        //工作进度
        recyclerView_of_job.setNestedScrollingEnabled(false);  //解决联动问题
        recyclerView_of_job.setLayoutManager(new LinearLayoutManager(getContext()));//布局问题
        initData_of_job();//数据
        Adapter_Job adapter_job =new Adapter_Job(mData_of_job,getContext());//适配器
        recyclerView_of_job.setAdapter(adapter_job);

        //工作进度
        getJob();

        //要情汇报
        Report_Recyclerview.setNestedScrollingEnabled(false);
        Report_Recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        initData_of_report();
        Report_Recyclerview.setAdapter(adapter_report);

        //日期初始化，时间选择
        initTime();
        last_Day_Button.setOnClickListener(new last_Day_Listener());
        next_Day_Button.setOnClickListener(new next_day_listener());
         return view;
    }

    private void getJob() {
        Subscription subscribe_getJob= NetWork.getAddJobService()
                .getJob(53,"2017-07-27")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AddJobBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<AddJobBean> addJobBeen) {
                      //  addJobBeen=new ArrayList<>();



                    }
                });
        subscriptions.add(subscribe_getJob);

    }


    public  void initData_of_job(){
        mData_of_job=new ArrayList<String>();
        for (int i=0;i<4;i++){
            mData_of_job.add((i+1)+": 7月28参加市工作会");
        }
    }


    protected void initData_of_report() {
        Subscription subscribe_getReportFeeling= NetWork.getReportFeelingService()
                .getRepReportFeeling(6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReportFeeling>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("tag", "onCompleted: ");
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("tag", "onError: ");
                    }
                    @Override
                    public void onNext(List<ReportFeeling> reportFeelings) {
                        Log.i("tag", "onNext: ");
                        mData_Of_Report=new ArrayList<>();
                        for (int i=0;i<reportFeelings.size();i++){
                            mData_Of_Report.add(reportFeelings.get(i));
                        }
                        Log.i("tag", "数据的大小Size  "+reportFeelings.size());
                        adapter_report=new Adapter_Report(getContext(),mData_Of_Report);
                    }
                });
        subscriptions.add(subscribe_getReportFeeling);


    }


    public class last_Day_Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(day==1){
                timeDisplayTextView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1));timeDisplayTextView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1));
                day=0;
            }else if(day==2){
                timeDisplayTextView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                day=1;
            }

        }
    }

    void initTime() {
        calendar = Calendar.getInstance();
        String getMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String getDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        timeDisplayTextView.setText(getMonth+"-"+getDay);
    }

    public class next_day_listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(day==0){
                timeDisplayTextView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                day=1;
            }else if(day==1){
                timeDisplayTextView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1)+"-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)+1));
                day=2;
            }
        }
    }





}
