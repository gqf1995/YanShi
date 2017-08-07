package com.example.hasee.yanshi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.LoginResult;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;
import com.example.hasee.yanshi.utils.NetUtils;
import com.example.hasee.yanshi.utils.PopupUtils;
import com.example.hasee.yanshi.utils.SettingsUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class login extends AppCompatActivity {

    String userName = null;
    String userPass = null;
    CompositeSubscription subscriptions = new CompositeSubscription();
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.name_EditText)
    EditText nameEditText;
    @BindView(R.id.pass_EditText)
    EditText passEditText;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_rememberPassword)
    CheckBox loginRememberPassword;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        //判断是否记住密码
        initLogin();
        //点击事件
        RxView.clicks(loginButton)
                //防止多点击
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (nameEditText.getText().toString().equals("")) {
                            nameEditText.setError("请输入用户名");
                        } else if (passEditText.getText().toString().equals("")) {
                            passEditText.setError("请输入密码");
                        } else {
                            login(nameEditText.getText().toString(), passEditText.getText().toString());
                        }
                    }
                });
    }

    public void initLogin() {
        loginRememberPassword.setChecked(SettingsUtils.isRememberPassword(getApplicationContext()));
        RxCompoundButton.checkedChanges(loginRememberPassword)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        SettingsUtils.setPrefRememberPassword(getApplicationContext(), aBoolean);
                    }
                });
        if (SettingsUtils.isRememberPassword(getApplicationContext())) {
            loginUser = realm.where(LoginUser.class).findFirst();
            if (loginUser != null) {
                nameEditText.setText(loginUser.getPhone_num());
                passEditText.setText(loginUser.getPassword());
            }
        } else {
            realm.beginTransaction();
            realm.delete(LoginUser.class);
            realm.commitTransaction();
        }
    }

    private void login(String phone, String pass) {
        if (!NetUtils.isConnected(this)) {
            Toast.makeText(this, "网络尚未连接", Toast.LENGTH_LONG).show();
        } else {
            // isLogin();
            //保存用户账户密码
            isLogin(phone, pass);
            loginButton.setEnabled(false);
            loginButton.setText("登陆中，请等待");
        }
    }

    LoginUser loginUser;

    private void isLogin(String phone, String pass) {
        Subscription subscribe_getAddJobService = NetWork.getNewApi()
                .login(phone, pass)//"17633905867", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginButton.setEnabled(true);
                        loginButton.setText("登陆");
                        Log.i("gqf","Throwable"+e.toString());
                        PopupUtils.showToast(getBaseContext(),"服务器连接失败");
                    }

                    @Override
                    public void onNext(LoginResult User) {

                        result(User);
                    }
                });
        subscriptions.add(subscribe_getAddJobService);

    }

    public void result(LoginResult User) {
        if (User.isCode()) {
            Log.i("gqf", "user" + User.getUser().toString());
            loginUser = realm.where(LoginUser.class).findFirst();
            //获取返回的用户信息并修改
            if (loginUser != null) {
                realm.beginTransaction();
                realm.delete(LoginUser.class);
                realm.commitTransaction();
            }
            loginUser = User.getUser();
            realm.beginTransaction();
            loginUser.setPhone_num(nameEditText.getText().toString());
            loginUser.setPassword(passEditText.getText().toString());
            realm.copyToRealmOrUpdate(loginUser);
            realm.commitTransaction();
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        } else {
            loginButton.setEnabled(true);
            loginButton.setText("登陆");
            Toast.makeText(getApplicationContext(), User.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

}
