package com.example.hasee.yanshi.report_feeling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.ReportFeelingDetail;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Report_Detail extends AppCompatActivity {
    private  TextView title;
    private  TextView content;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);
        title=(TextView)this.findViewById(R.id.report_title);
        content=(TextView)this.findViewById(R.id.report_content);
        int id=getIntent().getIntExtra("id",0);
        initDatas();
    }
    private void initDatas() {
        Subscription subscripttion= NetWork.getReportFeelingDetailService()
                .getRepReportFeelingDetail(43)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReportFeelingDetail>() {
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
                    public void onNext(ReportFeelingDetail reportFeelingDetails) {
                        Log.i("tag", "onNext: ");
                        title.setText(reportFeelingDetails.getTitle());
                        content.setText(reportFeelingDetails.getContent());
                    }
                });
        subscriptions.add(subscripttion);
    }

}
