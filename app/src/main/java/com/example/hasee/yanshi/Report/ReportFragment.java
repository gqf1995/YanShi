package com.example.hasee.yanshi.Report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.yanshi.Adapter.ReportInfoListAdapter;
import com.example.hasee.yanshi.Base.BaseFragment;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;
import com.example.hasee.yanshi.pojo.NewPojo.ReportInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/28.
 */

public class ReportFragment extends BaseFragment {
    @BindView(R.id.report_Recyclerview)
    RecyclerView reportRecyclerview;
    Unbinder unbinder;

    ReportInfoListAdapter reportInfoListAdapter;
    @BindView(R.id.load_more_list_view_ptr_frame)
    PtrClassicFrameLayout mPtrFrameLayout;


    public static ReportFragment newInstance(String param1) {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }


    public ReportFragment() {
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
        View view = inflater.inflate(R.layout.fragment_report_feeling, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData(realm.where(LoginUser.class).findFirst().getDepartment_id());
        initRefresh();
        return view;
    }
    public void initRefresh(){
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, reportRecyclerview, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //实现下拉刷新的功能
                Log.i("test", "-----onRefreshBegin-----");
                mPtrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(realm.where(LoginUser.class).findFirst().getDepartment_id());
                    }
                }, 500);
            }
        });
    }
    public void getData(int id) {
        Subscription subscription = NetWork.getNewApi().getListReportInfo(id)
                .subscribeOn(Schedulers.io())
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
                        initList(reportInfos);

                    }
                });
        compositeSubscription.add(subscription);
    }

    public void initList(List<ReportInfo> reportInfos) {
        if (reportInfoListAdapter == null) {
            reportInfoListAdapter = new ReportInfoListAdapter(getActivity(), reportInfos);
            reportRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            reportRecyclerview.setAdapter(reportInfoListAdapter);
            reportInfoListAdapter.setOnItemClickListener(new ReportInfoListAdapter.MyItemClickListener() {
                @Override
                public void OnClickListener(int position) {
                    //跳转详情页面
                    Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                    intent.putExtra(ReportDetailActivity.CONTENT_ID, reportInfoListAdapter.getDataItem(position).getId());
                    mListener.startNewActivity(intent);
                }
            });
        } else {
            reportInfoListAdapter.update(reportInfos);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
