package com.example.hasee.yanshi.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by johe on 2017/3/14.
 */

public class BaseFragment extends Fragment {

    protected Realm realm;
    protected CompositeSubscription compositeSubscription;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm= Realm.getDefaultInstance();
        compositeSubscription=new CompositeSubscription();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
        compositeSubscription.unsubscribe();
    }
}
