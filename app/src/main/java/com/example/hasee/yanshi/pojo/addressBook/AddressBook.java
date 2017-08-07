package com.example.hasee.yanshi.pojo.addressBook;

import java.util.List;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/7/26.
 */

public class AddressBook{

    @PrimaryKey
    int AddressBookiId;

    public int getAddressBookiId() {
        return AddressBookiId;
    }

    public void setAddressBookiId(int addressBookiId) {
        AddressBookiId = addressBookiId;
    }

    private List<MemberCountBean> memberCount;
    private List<DepartmentsBean> departments;

    public List<MemberCountBean> getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(List<MemberCountBean> memberCount) {
        this.memberCount = memberCount;
    }

    public List<DepartmentsBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentsBean> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "departments=" + departments +
                '}';
    }
}
