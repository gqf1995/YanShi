package com.example.hasee.yanshi.Msg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.yanshi.Base.BaseFragment;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.Report.ReportDetailActivity;
import com.example.hasee.yanshi.infor_bulletin.Infor_Bulletin_Adapter;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.InfoNotice;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;

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

public class MsgFragment extends BaseFragment {
    @BindView(R.id.infor_Bulletin_Recycler)
    RecyclerView inforBulletinRecycler;
    Unbinder unbinder;
    @BindView(R.id.load_more_list_view_ptr_frame)
    PtrClassicFrameLayout mPtrFrameLayout;

    public static MsgFragment newInstance(String param1) {
        Bundle args = new Bundle();
        MsgFragment fragment = new MsgFragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }


    public MsgFragment() {
    }

    Infor_Bulletin_Adapter infor_bulletin_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infor_bulletin, container, false);
        unbinder = ButterKnife.bind(this, view);
        ininData();
        initLoadMore();
        return view;
    }

    public void initLoadMore(){
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, inforBulletinRecycler, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //实现下拉刷新的功能
                Log.i("test", "-----onRefreshBegin-----");
                mPtrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ininData();
                    }
                }, 500);
            }
        });

    }

    void ininData() {
        Subscription subscription = NetWork.getNewApi().listInfoNotice(realm.where(LoginUser.class).findFirst().getDepartment_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<InfoNotice>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.refreshComplete();
                    }

                    @Override
                    public void onNext(List<InfoNotice> infoNotices) {
                        mPtrFrameLayout.refreshComplete();
                        initList(infoNotices);

                    }
                });
        compositeSubscription.add(subscription);
    }

    public interface mListener {
        public void startNewActivity(Intent intent);
    }

    private mListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (mListener) activity;

    }

    public void initList(List<InfoNotice> infoNotices) {
        if (infor_bulletin_adapter == null) {
            infor_bulletin_adapter = new Infor_Bulletin_Adapter(getContext(), infoNotices);
            inforBulletinRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            inforBulletinRecycler.setAdapter(infor_bulletin_adapter);
            infor_bulletin_adapter.setMyItemClickListener(new Infor_Bulletin_Adapter.MyItemClickListener() {
                @Override
                public void OnClickListener(int position) {
                    Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
                    intent.putExtra(ReportDetailActivity.CONTENT_ID, infor_bulletin_adapter.getDataItem(position).getId());
                    mListener.startNewActivity(intent);
                }
            });
        } else {
            infor_bulletin_adapter.updata(infoNotices);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
