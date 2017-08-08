package com.example.hasee.yanshi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hasee.yanshi.Base.BaseActivity;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.ChangePasswordResult;
import com.example.hasee.yanshi.pojo.NewPojo.LoginUser;
import com.example.hasee.yanshi.utils.NetUtils;
import com.example.hasee.yanshi.utils.PopupUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/8/8.
 */

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.name_EditText)
    EditText nameEditText;
    @BindView(R.id.old_pass_EditText)
    EditText oldPassEditText;
    @BindView(R.id.new_pass_EditText)
    EditText newPassEditText;
    @BindView(R.id.login_button)
    Button loginButton;

    LoginUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        loginUser = realm.where(LoginUser.class).findFirst();
        nameEditText.setText(loginUser.getPhone_num());
        //点击事件
        RxView.clicks(loginButton)
                //防止多点击
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (nameEditText.getText().toString().equals("")) {
                            nameEditText.setError("请输入用户名");
                        } else if (!nameEditText.getText().toString().equals(loginUser.getPhone_num())) {
                            nameEditText.setError("非当前用户名");
                        } else if (oldPassEditText.getText().toString().equals("")) {
                            oldPassEditText.setError("请输入旧密码");
                        } else if (!nameEditText.getText().toString().equals(loginUser.getPassword())) {
                            oldPassEditText.setError("旧密码错误");
                        } else if (newPassEditText.getText().toString().equals("")) {
                            newPassEditText.setError("请输入新密码");
                        } else if (oldPassEditText.getText().toString().equals(newPassEditText.getText().toString())) {
                            newPassEditText.setError("新旧密码不能相同");
                        } else {
                            changePassword(loginUser.getId(), newPassEditText.getText().toString());
                        }
                    }
                });
    }

    public void changePassword(int id, String password) {
        if (!NetUtils.isConnected(this)) {
            Toast.makeText(this, "网络尚未连接", Toast.LENGTH_LONG).show();
        } else {
            // isLogin();
            //保存用户账户密码
            isLogin(id, password);
            loginButton.setEnabled(false);
            loginButton.setText("修改中，请等待");
        }
    }

    private void isLogin(int id, String pass) {
        Subscription subscribe_getAddJobService = NetWork.getNewApi()
                .updatePasswordByUserId(id, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChangePasswordResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginButton.setEnabled(true);
                        loginButton.setText("修改");
                        Log.i("gqf", "Throwable" + e.toString());
                        PopupUtils.showToast(getBaseContext(), "服务器连接失败");
                    }

                    @Override
                    public void onNext(ChangePasswordResult changePasswordResult) {
                        readResult(changePasswordResult);
                    }
                });
        compositeSubscription.add(subscribe_getAddJobService);

    }

    public void readResult(ChangePasswordResult changePasswordResult) {
        if (changePasswordResult.isCode()) {
            loginUser = realm.where(LoginUser.class).findFirst();
            realm.beginTransaction();
            loginUser.setPassword(newPassEditText.getText().toString());
            realm.copyToRealmOrUpdate(loginUser);
            realm.commitTransaction();
            PopupUtils.showToast(getBaseContext(), "修改成功");
            finish();
        } else {
            loginButton.setEnabled(true);
            loginButton.setText("修改");
            PopupUtils.showToast(getBaseContext(), changePasswordResult.getMessage());
        }
    }
}
