package com.example.hasee.yanshi.address_book.tree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.hasee.yanshi.Contact.ContactDialogFragment;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.example.hasee.yanshi.pojo.addressBook.SysUserBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private Context context;
    List<String[]> data;//第三级数据
    String[] headers;//第二级数据
    ContactDialogFragment contactDialogFragment;
    Gson gson;

    public interface Linsener {
        public void onClickPeople(ContactEvent contactEvent);
    }

    Linsener mLinsener;

    public void setmLinsener(Linsener mLinsener) {
        this.mLinsener = mLinsener;
    }

    public SecondLevelAdapter(Context context, String[] headers, List<String[]> data) {
        this.context = context;
        this.data = data;
        this.headers = headers;
        gson = new Gson();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //父级
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_second, null);
        TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);

        String groupText = getGroup(groupPosition).toString();
        text.setText(groupText + "(" + data.get(groupPosition).length + "人)");
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String[] childData;
        childData = data.get(groupPosition);
        return childData[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_third, null);
        final TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);

        String[] childArray = data.get(groupPosition);
        Log.i("length", "" + childArray.length);
        for (int i = 0; i < childArray.length; i++) {
            Log.i("三级", "" + childArray[i]);
        }
        String text = childArray[childPosition];
        SysUserBean sysUserBean = gson.fromJson(text, SysUserBean.class);
        textView.setText(sysUserBean.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLinsener != null) {
                    mLinsener.onClickPeople(new ContactEvent(gson.fromJson(data.get(groupPosition)[childPosition], SysUserBean.class)));
                } else {
                    EventBus.getDefault().post(new ContactEvent(gson.fromJson(data.get(groupPosition)[childPosition], SysUserBean.class)));
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);
        return children.length;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}