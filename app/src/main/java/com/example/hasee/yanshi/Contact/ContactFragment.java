package com.example.hasee.yanshi.Contact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.yanshi.Base.BaseFragment;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.address_book.tree.ThreeLevelListAdapter;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.example.hasee.yanshi.pojo.addressBook.AddressBook;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/28.
 */

public class ContactFragment extends BaseFragment {
    @BindView(R.id.expandible_listview)
    ExpandableListView expandibleListview;
    Unbinder unbinder;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.top)
    LinearLayout top;

    public static ContactFragment newInstance(String param1) {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactFragment() {
    }


    AddressBook addressBook;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);
        unbinder = ButterKnife.bind(this, view);
        //addressBook=realm.where(AddressBook.class).findFirst();
        gson = new Gson();
//        addressBook = gson.fromJson(getResources().getString(R.string.demojson), AddressBook.class);
//        if (addressBook != null) {
//            initList(addressBook);
//        }
        initAddressBook();

        return view;
    }

    private void initAddressBook() {
        Subscription subscribe_getAddressBook = NetWork.getNewApi().getAddressBook()
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
                        addressBook = address;
                        initList(addressBook);
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
        threeLevelListAdapter = new ThreeLevelListAdapter(getActivity(), firstDatas, secondLevel, data);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        if(etSearch.getText().toString().equals("")){
            etSearch.setError("请输入人名");
        }else{
            for(int i=0;i<contactEvents.size();i++){
                Log.i("gqf","contactEvents"+contactEvents.get(i));
                if(contactEvents.get(i).getSysUser().getName().equals(etSearch.getText().toString())){
                    EventBus.getDefault().post(contactEvents.get(i));
                    return;
                }
            }
            Toast.makeText(getActivity(),"没有查询到此人",Toast.LENGTH_SHORT).show();
        }
    }
}
