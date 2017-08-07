package com.example.hasee.yanshi.address_book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.address_book.tree.ThreeLevelListAdapter;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.addressBook.AddressBook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Address_book_fragment extends Fragment {
    ExpandableListView expandableListView;
    ThreeLevelListAdapter threeLevelListAdapterAdapter;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

  //  String[] parent = new String[]{"公安局", "交通局"};

  //  String[] movies = new String[]{"刑警大队","交警大队","治安警察大队"};
   // String[] games = new String[]{"人事科","综合科","财务科","办公室"};

   // String[] horror = new String[]{"陈宏子"};
   /* String[] action = new String[]{"曹丽娜"};
    String[] thriller = new String[]{ "陈逸群"};

    String[] fps = new String[]{ "周志光"};
    String[] moba = new String[]{"王琳" };
    String[] rpg = new String[]{ "邓天文"};
    String[] racing = new String[]{"李璇"};*/


   // LinkedHashMap<String, String[]> thirdLevelMovies = new LinkedHashMap<>();
   // LinkedHashMap<String, String[]> thirdLevelGames = new LinkedHashMap<>();
   List<String[]> secondLevel = new ArrayList<>();

    LinkedHashMap<String, String[]> thirdLevel = new LinkedHashMap<>();

    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();

    public static Address_book_fragment newInstance(String param1) {
        Bundle args = new Bundle();
        Address_book_fragment fragment = new Address_book_fragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
    public Address_book_fragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // secondLevel.add(movies);
        //secondLevel.add(games);


        /*thirdLevelMovies.put(movies[0], horror);
        thirdLevelMovies.put(movies[1], action);
        thirdLevelMovies.put(movies[2], thriller);

        thirdLevelGames.put(games[0], fps);
        thirdLevelGames.put(games[1], moba);
        thirdLevelGames.put(games[2], rpg);
        thirdLevelGames.put(games[3], racing);*/

/*

       data.add(thirdLevelMovies);
        data.add(thirdLevelGames);
*/

        initAddressBook();

        View view=inflater.inflate(R.layout.fragment_address_book, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);
        expandableListView.setAdapter(threeLevelListAdapterAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        return view;
    }

    private void initAddressBook() {
        Subscription subscribe_getAddressBook= NetWork.getAddressBookService().getAddressBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressBook>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Address_book_fragment", "onCompleted: ");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Address_book_fragment", "onError: ");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddressBook addressBook) {
                        //第一，二，三层循环分别获取一，二，三级数据，
                        Log.i("Address_book_fragment", "onNext: ");
                        //一级数组
                        int firstSize=addressBook.getDepartments().size();
                        String [] firstDatas=new String[firstSize];

                        for(int i=0;i<firstSize;i++){
                            String [] secondDatas=new String[addressBook.getDepartments().get(i).getChildNodes().size()];
                            firstDatas[i]=addressBook.getDepartments().get(i).getText();
                            Log.i("一级数组项", ""+firstDatas[i]);
                            int secondSize=addressBook.getDepartments().get(i).getChildNodes().size();
                            for(int j=0;j<secondSize;j++){
                                //二层数据
                                secondDatas[j]=addressBook.getDepartments().get(i).getChildNodes().get(j).getText();
                                Log.i("二级数组项", ""+secondDatas[j]);
                                //三层数据
                                int thirdSize=addressBook.getDepartments().get(i).getChildNodes().get(j).getChildNodes().size();
                                String [] arrthirdDatas=new String[thirdSize];
                                for(int z=0;z<thirdSize;z++){
                                    arrthirdDatas[z]=addressBook.getDepartments().get(i).getChildNodes().get(j).getChildNodes().get(z).getText();
                                    Log.i("三级数组项", ""+arrthirdDatas[z]);
                                }
                                thirdLevel.put(secondDatas[j],arrthirdDatas);
                            }
                            secondLevel.add(secondDatas);
                            data.add(thirdLevel);
                        }
                          for(int f=0;f<secondLevel.size();f++){
                            for(int j=0;j<secondLevel.get(f).length;j++){
                                Log.i("Address_book_fragment", "SecondDatas"+f+j+secondLevel.get(f)[j]);
                            }
                          }
                        threeLevelListAdapterAdapter = new ThreeLevelListAdapter(getActivity(), firstDatas, secondLevel, data);

                           /* String [] a=secondList.get(0);
                            Log.i("a", "onNext: "+a+"   "+secondList.size()+threeList.size());
                            String [] b=secondList.get(1);
                            String [] c=secondList.get(2);
*/
                      /*  for(int i=0;i<secondList.size();i++){
                            secondLevel.add(secondList.get(i));
                        }*/

                       /* secondLevel.add(a);
                        secondLevel.add(b);
                        secondLevel.add(c);*/
                       /*for(int j=0;j<secondList.size();j++){
                           int temp=0;
                           secondLevel.add(secondList.get(j));
                           LinkedHashMap<String, String[]> thirddLevel = new LinkedHashMap<>();
                           for (int s=0;s<secondList.get(j).length;s++){
                               thirddLevel.put(secondList.get(j)[temp],threeList.get(temp));
                               temp++;
                           }
                           data.add((LinkedHashMap<String, String[]>)new LinkedHashMap<>().put(thirddLevel.keySet().iterator().next().toString(),thirddLevel.values().toArray()));
                       }*/

                          /*  thirddLevel.put(a[0],threeList.get(0));
                            thirddLevel.put(a[1],threeList.get(1));

                            thirddLeve2.put(b[0],threeList.get(2));
                            thirddLeve2.put(b[1],threeList.get(3));

                            thirddLeve3.put(c[0],threeList.get(4));

                            data.add(thirddLevel);
                            data.add(thirddLeve2);
                            data.add(thirddLeve3);*/


                     /*   for(int s=0;s<firstDatas.length;s++){
                            Log.i("firstDatas", " "+firstDatas[s]);
                        }

                       /* for(int f=0;f<secondLevel.size();f++){
                            for(int j=0;j<secondLevel.get(f).length;j++){
                                Log.i("Address_book_fragment", "SecondDatas"+f+j+secondLevel.get(f)[j]);
                            }
                        }*/
                    }
                });
        subscriptions.add(subscribe_getAddressBook);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subscriptions.unsubscribe();
    }

}
