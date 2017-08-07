package com.example.hasee.yanshi.report_feeling;

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

public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.MyViewHolder>{
    private ItemClickListener mItemClickListener;
    Context context;
    List<ReportFeeling> mDatas;
    LayoutInflater inflater;

    public  Report_Adapter(Context context,List<ReportFeeling> mDatas){
        this.mDatas=mDatas;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.report_feeling_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,mItemClickListener);
        return myViewHolder;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position).getTitle());
        holder.createTime.setText(mDatas.get(position).getCreatetime());
    }

   public interface ItemClickListener{
       void OnClickListener(int position);
   }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemClickListener mItemClickListener;
          TextView title;
          TextView createTime;

        public MyViewHolder(View itemView,ItemClickListener mItemClickListener) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title_item);
            createTime=(TextView)itemView.findViewById(R.id.createTime);
            this.mItemClickListener = mItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.OnClickListener(getPosition());
            }

        }
    }

}
