package com.example.hasee.yanshi.address_book.tree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.hasee.yanshi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ThreeLevelListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "daniel";
    String[] parentHeaders;//一级数据
    List<String[]> secondLevel;//二级数据
    private Context context;
    List<LinkedHashMap<String, String[]>> data;//三级数据


    SecondLevelAdapter.Linsener mLinsener;
    //List<Departments> Ddatas;//一级数据


    public void setmLinsener(SecondLevelAdapter.Linsener mLinsener) {
        this.mLinsener = mLinsener;
    }

    public ThreeLevelListAdapter(Context context, String[] parentHeader, List<String[]> secondLevel, List<LinkedHashMap<String, String[]>> data) {
        this.context = context;
        this.parentHeaders = parentHeader;
        this.secondLevel = secondLevel;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return parentHeaders.length;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }
    @Override
    public Object getChild(int group, int child) {
        return child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_first, null);
            TextView text = (TextView) convertView.findViewById(R.id.rowParentText);
        Log.e(TAG, "第一级：: "+parentHeaders[groupPosition] );
          text.setText(parentHeaders[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        //获得二级一组数据，将其放到数组里面
        //三级数据
        List<String[]> childData = new ArrayList<>();
        HashMap<String, String[]> secondLevelData=data.get(groupPosition);

        String[] headers = secondLevel.get(groupPosition);
        for (int i = 0; i < headers.length; i++) {
            Log.e(TAG, "第二级：: "+headers[i] );
            childData.add(secondLevelData.get(headers[i]));
        }

        Log.e(TAG, "第三级数量：: "+childData.size() );


        secondLevelAdapter=new SecondLevelAdapter(context, headers,childData);
        secondLevelELV.setAdapter(secondLevelAdapter);
        secondLevelELV.setGroupIndicator(null);
        secondLevelELV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    secondLevelELV.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        secondLevelAdapter.setmLinsener(mLinsener);
        return secondLevelELV;
    }
    SecondLevelAdapter secondLevelAdapter;
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
