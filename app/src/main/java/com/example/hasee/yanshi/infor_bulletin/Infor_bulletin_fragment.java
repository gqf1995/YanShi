package com.example.hasee.yanshi.infor_bulletin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.yanshi.R;

import java.util.ArrayList;
import java.util.List;


public class Infor_bulletin_fragment extends Fragment {
    List<String> mDatas;

    public static Infor_bulletin_fragment newInstance(String param1) {
        Bundle args = new Bundle();
        Infor_bulletin_fragment fragment = new Infor_bulletin_fragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Infor_bulletin_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_infor_bulletin,container,false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.infor_Bulletin_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ininData();
        return view;
    }

    void ininData(){
        mDatas=new ArrayList<String>();
        for(int i=0;i<10;i++){
            mDatas.add("市区举办突发地质灾害演练，洛阳武警冒雨练兵...");
        }
    }

}
