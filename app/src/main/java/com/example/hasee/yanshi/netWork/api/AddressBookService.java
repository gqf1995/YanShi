package com.example.hasee.yanshi.netWork.api;


import com.example.hasee.yanshi.pojo.addressBook.AddressBook;
import com.example.hasee.yanshi.pojo.addressBook.ChildNodesBean;
import com.example.hasee.yanshi.pojo.addressBook.ChildNodesBeanX;
import com.example.hasee.yanshi.pojo.addressBook.DepartmentsBean;
import com.example.hasee.yanshi.pojo.addressBook.MemberCountBean;
import com.example.hasee.yanshi.pojo.addressBook.SysUserBean;

import retrofit2.http.GET;
import rx.Observable;

public interface AddressBookService {
    //通讯录
    @GET("getAddress_Book")
    Observable<AddressBook> getAddressBook();
    @GET("getDepartments")
    Observable<DepartmentsBean> getDepartments();
    @GET("getMemberCount")
    Observable<MemberCountBean> getMemberCount();
    @GET("getChildNodesBeanX")
    Observable<ChildNodesBeanX> getChildNodesBeanX();

    @GET("getChildNodesBean")
    Observable<ChildNodesBean> getChildNodesBean();

    @GET("getSysUserBean")
    Observable<SysUserBean> getSysUserBean();

}
