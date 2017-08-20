package com.example.hasee.yanshi.pojo.NewPojo;

import com.example.hasee.yanshi.pojo.addressBook.SysUserBean;

import java.util.List;

/**
 * Created by hasee on 2017/8/16.
 * {
 "ChildNodes": [],
 "parentnodes": "de119",
 "hasChildren": false,
 "hierarchy": 3,
 "id": "18",
 "sysUser": {
 "user_division": "我是公检法一把手",
 "createtime": "2017-08-16 09:54:23",
 "password": "123456",
 "department_id": 119,
 "user_position": "公检法一把手",
 "name": "甜甜",
 "phone_num": "17630509362",
 "sort": 1,
 "id": 18,
 "user_rank": "01",
 "updatetime": "2017-08-16 09:59:54"
 },
 "text": "甜甜"
 }
 */

public class ContentTree {
    /**
     * ChildNodes : []
     * parentnodes : de119
     * hasChildren : false
     * hierarchy : 3
     * id : 18
     * sysUser : {"user_division":"我是公检法一把手","createtime":"2017-08-16 09:54:23","password":"123456","department_id":119,"user_position":"公检法一把手","name":"甜甜","phone_num":"17630509362","sort":1,"id":18,"user_rank":"01","updatetime":"2017-08-16 09:59:54"}
     * text : 甜甜
     */

    private String parentnodes;
    private boolean hasChildren;
    private int hierarchy;
    private String id;
    private SysUserBean sysUser;
    private String text;
    private List<ContentTree> ChildNodes;

    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getParentnodes() {
        return parentnodes;
    }

    public void setParentnodes(String parentnodes) {
        this.parentnodes = parentnodes;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SysUserBean getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserBean sysUser) {
        this.sysUser = sysUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ContentTree> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<ContentTree> ChildNodes) {
        this.ChildNodes = ChildNodes;
    }


    @Override
    public String toString() {
        return "ContentTree{" +
                "parentnodes='" + parentnodes + '\'' +
                ", hasChildren=" + hasChildren +
                ", hierarchy=" + hierarchy +
                ", id='" + id + '\'' +
                ", sysUser=" + sysUser +
                ", text='" + text + '\'' +
                ", ChildNodes=" + ChildNodes +
                '}';
    }
}




















