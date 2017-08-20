package com.example.hasee.yanshi.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hasee on 2017/7/29.
 */

public class ContactDialogFragment extends DialogFragment {

    ContactEvent contactEvent;
    @BindView(R.id.name_txt)
    TextView nameTxt;
    @BindView(R.id.position_txt)
    TextView positionTxt;
    @BindView(R.id.phone_btn)
    Button phoneBtn;
    @BindView(R.id.sms_btn)
    Button smsBtn;
    @BindView(R.id.dimss_btn)
    Button dimssBtn;
    Unbinder unbinder;
    @BindView(R.id.division_txt)
    TextView divisionTxt;

    public void setContactEvent(ContactEvent contactEvent) {
        this.contactEvent = contactEvent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_fragment_contact, container);
        unbinder = ButterKnife.bind(this, view);
        nameTxt.setText(contactEvent.getSysUser().getName());
        divisionTxt.setText("分工:" +contactEvent.getSysUser().getUser_division());
        positionTxt.setText( contactEvent.getSysUser().getUser_position());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.phone_btn, R.id.sms_btn, R.id.dimss_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_btn:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactEvent.getSysUser().getPhone_num()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                break;
            case R.id.sms_btn:
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + contactEvent.getSysUser().getPhone_num()));
                getActivity().startActivity(sendIntent);
                break;
            case R.id.dimss_btn:
                this.dismiss();
                break;

        }
    }
}
