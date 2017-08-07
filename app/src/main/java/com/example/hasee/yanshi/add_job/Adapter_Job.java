package com.example.hasee.yanshi.add_job;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.yanshi.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */

public class Adapter_Job extends RecyclerView.Adapter<Adapter_Job.myViewHolder> {
    List<String> mDatas;
    Context context;
    LayoutInflater layoutInflater;
    public Adapter_Job(List<String> mDatas,Context context){
        this.mDatas=mDatas;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.job_of_recycler_tem,parent,false);
        myViewHolder myViewHolder=new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public myViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.TextView_Job_Content);

        }
    }


}
