package com.example.hasee.yanshi.Report;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.hasee.yanshi.Base.BaseActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.DetailReportInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hasee on 2017/7/29.
 */

public class ReportDetailActivity extends BaseActivity {

    int contentId;
    public static final String CONTENT_ID = "contentId";
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.time_txt)
    TextView timeTxt;
    @BindView(R.id.people_txt)
    TextView peopleTxt;
    @BindView(R.id.context_txt)
    TextView contextTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.html_web)
    WebView htmlWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        contentId = getIntent().getIntExtra(CONTENT_ID, 0);
        getData(contentId);
        Log.i("gqf", CONTENT_ID + contentId);
        toolbar.setTitle("详情");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getData(int id) {
        Subscription subscription = NetWork.getNewApi().getDetailReportInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DetailReportInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DetailReportInfo> detailReportInfos) {
                        Log.i("gqf", "detailReportInfos" + detailReportInfos.toString());
                        initView(detailReportInfos);
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void initView(List<DetailReportInfo> detailReportInfos) {
        DetailReportInfo detailReportInfo = detailReportInfos.get(0);
        titleTxt.setText(detailReportInfo.getTitle());
        timeTxt.setText(detailReportInfo.getUpdatetime());
        peopleTxt.setText(detailReportInfo.getName());


        WebSettings settings = htmlWeb.getSettings();
        settings.setAppCacheEnabled(true);//设置启动缓存
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//缓存模式
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//4.4以下版本自适应页面大小 不能左右滑动
        //        1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
        //        2.NORMAL：正常显示不做任何渲染
        //        3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
        settings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        settings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        settings.setTextZoom(200);//字体大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);//支持js
        settings.setSupportZoom(true);//仅支持双击缩放r

        settings.setBlockNetworkImage(false);//阻止图片网络数据
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//图片加载放在最后

        settings.setBuiltInZoomControls(true);// 出现放大缩小提示
        settings.setDisplayZoomControls(false);//隐藏缩放按钮
        settings.setDefaultTextEncodingName("utf-8");

        htmlWeb.setVerticalScrollBarEnabled(false);//滚动条不显示
        htmlWeb.setHorizontalScrollBarEnabled(false);
        htmlWeb.setInitialScale(57);//最小缩放等级
        htmlWeb.loadData(detailReportInfo.getContent(),"text/html; charset=UTF-8", null);

//        Html.ImageGetter imgGetter = new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                Drawable drawable = null;
//                drawable = Drawable.createFromPath(source);  // Or fetch it from the URL
//                // Important
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                        .getIntrinsicHeight());
//                return drawable;
//            }
//        };
        //contextTxt.setText(Html.fromHtml(detailReportInfo.getContent(), imgGetter, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
