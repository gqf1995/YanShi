package com.example.hasee.yanshi.Base;

import android.os.Bundle;

import io.realm.Realm;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by johe on 2017/3/14.
 */

public class BaseActivity extends SwipeBackActivity {

    protected Realm realm;
    protected CompositeSubscription compositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm= Realm.getDefaultInstance();
        compositeSubscription=new CompositeSubscription();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeSubscription.unsubscribe();
    }

}