package com.example.hasee.yanshi.pojo.NewPojo.Event;

import com.example.hasee.yanshi.pojo.addressBook.SysUserBean;

/**
 * Created by hasee on 2017/7/29.
 */

public class ContactEvent {
    private SysUserBean sysUser;

    public ContactEvent(SysUserBean sysUser){
        this.sysUser=sysUser;
    }

    public SysUserBean getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserBean sysUser) {
        this.sysUser = sysUser;
    }
}
