package com.example.hasee.yanshi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.NewPojo.ReportInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hasee on 2017/7/29.
 */

public class ReportInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context mContext;
    private List<ReportInfo> datas;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;
    CompositeSubscription compositeSubscription;

    public ReportInfo getDataItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public ReportInfoListAdapter(Context mContext, List<ReportInfo> mDatas) {
        this.mContext = mContext;
        this.datas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
        compositeSubscription = new CompositeSubscription();
    }

    public void update(List<ReportInfo> mDatas) {
        this.datas = mDatas;
        this.notifyDataSetChanged();
    }

    public int getLayout() {
        return R.layout.report_feeling_item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        //View v = mLayoutInflater.inflate(R.layout.my_order_list_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.createTime.setText(datas.get(position).getCreatetime());
        mHolder.titleItem.setText(datas.get(position).getTitle());
        if (position % 2 == 0) {

        } else {

        }
        mHolder.rootLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnClickListener(position);
                }
            }
        });
        if (position % 3 == 1) {
            mHolder.image.setImageResource(R.drawable.in2);
        } else if (position % 3 == 2) {
            mHolder.image.setImageResource(R.drawable.tu2);
        } else {
            mHolder.image.setImageResource(R.drawable.tu3);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public interface MyItemClickListener {
        public void OnClickListener(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title_item)
        TextView titleItem;
        @BindView(R.id.createTime)
        TextView createTime;
        @BindView(R.id.root_lin)
        LinearLayout rootLin;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}