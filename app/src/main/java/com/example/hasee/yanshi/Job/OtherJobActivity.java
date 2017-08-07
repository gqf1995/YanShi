package com.example.hasee.yanshi.Job;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.yanshi.Base.BaseActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.address_book.tree.SecondLevelAdapter;
import com.example.hasee.yanshi.address_book.tree.ThreeLevelListAdapter;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.example.hasee.yanshi.pojo.addressBook.AddressBook;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/30.
 */

public class OtherJobActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.expandible_listview)
    ExpandableListView expandibleListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_job);
        ButterKnife.bind(this);
        toolbar.setTitle("可查看人员");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initAddressBook();
    }

    private void initAddressBook() {
        Subscription subscribe_getAddressBook = NetWork.getNewApi().getOtherLeaderWorkBefore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressBook>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Address_book_fragment", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Address_book_fragment", "onError: " + e.toString());

                    }

                    @Override
                    public void onNext(AddressBook address) {
                        //本地数据库更新
                        initList(address);
                    }
                });
        compositeSubscription.add(subscribe_getAddressBook);
    }

    List<String[]> secondLevel;
    LinkedHashMap<String, String[]> thirdLevel;
    List<LinkedHashMap<String, String[]>> data;
    ThreeLevelListAdapter threeLevelListAdapter;
    List<ContactEvent> contactEvents;

    public void initList(AddressBook addressBook) {
        Gson gson=new Gson();
        Log.i("Address_book_fragment", "addressBook: "+addressBook.toString());
        secondLevel = new ArrayList<>();
        thirdLevel = new LinkedHashMap<>();
        data = new ArrayList<>();
        contactEvents=new ArrayList<>();

        //第一，二，三层循环分别获取一，二，三级数据，
        Log.i("Address_book_fragment", "onNext: ");
        //一级数组
        int firstSize = addressBook.getDepartments().size();
        //第一层
        String[] firstDatas = new String[firstSize];

        for (int i = 0; i < firstSize; i++) {
            //第二层
            String[] secondDatas = new String[addressBook.getDepartments().get(i).getChildNodes().size()];
            firstDatas[i] = addressBook.getDepartments().get(i).getText();
            Log.i("一级数组项", "" + firstDatas[i]);
            int secondSize = addressBook.getDepartments().get(i).getChildNodes().size();
            for (int j = 0; j < secondSize; j++) {
                //二层数据
                secondDatas[j] = addressBook.getDepartments().get(i).getChildNodes().get(j).getText();
                Log.i("二级数组项", "" + secondDatas[j]);
                //三层数据
                int thirdSize = addressBook.getDepartments().get(i).getChildNodes().get(j).getChildNodes().size();
                String[] arrthirdDatas = new String[thirdSize];
                for (int z = 0; z < thirdSize; z++) {
                    arrthirdDatas[z] = gson.toJson(addressBook.getDepartments().get(i).getChildNodes().get(j).getChildNodes().get(z).getSysUser());
                    Log.i("三级数组项", "" + arrthirdDatas[z]);
                    contactEvents.add(new ContactEvent(addressBook.getDepartments().get(i).getChildNodes().get(j).getChildNodes().get(z).getSysUser()));
                }
                thirdLevel.put(secondDatas[j], arrthirdDatas);
            }
            secondLevel.add(secondDatas);
            data.add(thirdLevel);
        }
        for (int f = 0; f < secondLevel.size(); f++) {
            for (int j = 0; j < secondLevel.get(f).length; j++) {
                Log.i("Address_book_fragment", "SecondDatas" + f + j + secondLevel.get(f)[j]);
            }
        }
        threeLevelListAdapter = new ThreeLevelListAdapter(this, firstDatas, secondLevel, data);
        expandibleListview.setAdapter(threeLevelListAdapter);
        expandibleListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandibleListview.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        threeLevelListAdapter.setmLinsener(new SecondLevelAdapter.Linsener() {
            @Override
            public void onClickPeople(ContactEvent contactEvent) {
                //查看此人工作安排
                toOtherActivity(contactEvent);
            }
        });
    }
    Intent intent;
    public void toOtherActivity(ContactEvent contactEvent){
        Gson gson=new Gson();
        intent=new Intent(OtherJobActivity.this,GetOtherJobActivity.class);
        intent.putExtra("ContactEvent",gson.toJson(contactEvent));
        startActivity(intent);
    }
    @OnClick(R.id.search)
    public void onViewClicked() {
        if(etSearch.getText().toString().equals("")){
            etSearch.setError("请输入人名");
        }else{
            for(int i=0;i<contactEvents.size();i++){
                Log.i("gqf","contactEvents"+contactEvents.get(i));
                if(contactEvents.get(i).getSysUser().getName().equals(etSearch.getText().toString())){
                    //跳转详情
                    toOtherActivity(contactEvents.get(i));
                    return;
                }
            }
            Toast.makeText(this,"没有查询到此人",Toast.LENGTH_SHORT).show();
        }
    }


}
