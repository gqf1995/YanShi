package com.example.hasee.yanshi.report_feeling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.ReportFeeling;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Report_feeling_fragment extends Fragment {
    RecyclerView recyclerView;
    private List<ReportFeeling> mDatas;
    Report_Adapter report_adapter;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public static Report_feeling_fragment newInstance(String param1) {
        Bundle args = new Bundle();
        Report_feeling_fragment fragment = new Report_feeling_fragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Report_feeling_fragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_feeling,container,false);
        initData();
        recyclerView=(RecyclerView)view.findViewById(R.id.report_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      /*  report_adapter.setOnItemClickListener(new Report_Adapter.ItemClickListener() {
            @Override
            public void OnClickListener(int position) {
                Toast.makeText(getContext(),"你点击了"+position,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),Report_Detail.class);
                intent.putExtra("id",mDatas.get(position).getId());
                startActivity(intent);

            }
        });*/

    /*    report_adapter.setOnItemClickListener(new Report_Adapter.HomeBankActivitysItemClickListener() {
            @Override
            public void OnClickListener(int position) {
                Toast.makeText(getContext(),"你点击了"+position,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),Report_Detail.class);
                intent.putExtra("id",mDatas.get(position).getId());
                startActivity(intent);
            }
        });*/
        recyclerView.setAdapter(report_adapter);
      //  recyclerView.setOnClickListener(report_adapter.setOnItemClickListener());
        return view;
    }


    protected void initData() {
        Subscription subscribe_getReportFeeling= NetWork.getReportFeelingService()
                .getRepReportFeeling(6)
                .subscribeOn(Schedulers.io())
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
                         mDatas=new ArrayList<>();
                        for (int i=0;i<reportFeelings.size();i++){
                            mDatas.add(reportFeelings.get(i));
                        }
                        Log.i("tag", "数据的大小Size  "+reportFeelings.size());
                        report_adapter=new Report_Adapter(getContext(),mDatas);
                    }
                });
        subscriptions.add(subscribe_getReportFeeling);


    }



}
