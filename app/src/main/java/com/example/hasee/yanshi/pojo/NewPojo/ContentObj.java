package com.example.hasee.yanshi.pojo.NewPojo;

import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 */

public class ContentObj {

    List<ContentTree> departments;

    public List<ContentTree> getDepartments() {
        return departments;
    }

    public void setDepartments(List<ContentTree> departments) {
        this.departments = departments;
    }



    @Override
    public String toString() {
        return "ContentObj{" +
                "departments=" + departments +
                '}';
    }
}
