package com.example.hasee.yanshi.add_job;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.ReportFeeling;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Adapter_Report extends RecyclerView.Adapter<Adapter_Report.MyViewHoler> {
     private List<ReportFeeling> mData;
    Context context;
    LayoutInflater layoutInflater;

    public  Adapter_Report(Context context,List<ReportFeeling> mData){
        this.mData=mData;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.job_infor_item,parent,false);
        MyViewHoler myViewHoler=new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        holder.textView.setText(mData.get(position).getTitle());
        holder.content.setText(mData.get(position).getCreatetime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {
        TextView textView;
        TextView content;
        public MyViewHoler(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.item_title_item);
            content=(TextView)itemView.findViewById(R.id.createTime);
        }
    }


}
