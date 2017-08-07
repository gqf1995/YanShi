package com.example.hasee.yanshi.infor_bulletin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.NewPojo.InfoNotice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Infor_Bulletin_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<InfoNotice> mData;
    LayoutInflater layoutInflater;

    MyItemClickListener myItemClickListener;
    public InfoNotice getDataItem(int position) {
        return mData == null ? null : mData.get(position);
    }
    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public Infor_Bulletin_Adapter(Context context, List<InfoNotice> mData) {
        this.context = context;
        this.mData = mData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.infor_bulletin_item, parent, false);
        RecyclerView.ViewHolder viewHoler = new ViewHolder(view);

        return viewHoler;
    }
    public interface MyItemClickListener {
        public void OnClickListener(int position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.itemTitleItem.setText(mData.get(position).getTitle());
        mHolder.timeTxt.setText(mData.get(position).getCreatetime());
        mHolder.relRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.OnClickListener(position);
            }
        });
        if(position%3==0){
            mHolder.itemImageView.setImageResource(R.drawable.in2);
        }else if(position%3==1){
            mHolder.itemImageView.setImageResource(R.drawable.tu2);
        }else{
            mHolder.itemImageView.setImageResource(R.drawable.tu3);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updata(List<InfoNotice> Data) {
        mData = Data;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_ImageView)
        ImageView itemImageView;
        @BindView(R.id.item_title_item)
        TextView itemTitleItem;
        @BindView(R.id.time_txt)
        TextView timeTxt;
        @BindView(R.id.rel_root)
        RelativeLayout relRoot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
