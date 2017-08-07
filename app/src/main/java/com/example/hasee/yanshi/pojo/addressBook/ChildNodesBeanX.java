package com.example.hasee.yanshi.pojo.addressBook;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class ChildNodesBeanX {
    /**
     * ChildNodes : [{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"7","sysUser":{"user_division":"分工","createtime":"2017-07-18 15:10:31","department_id":7,"name":"HHH","user_position":"职位","phone_num":"17633905867","login_num":"0","id":7,"user_rank":"02","updatetime":"2017-07-21 11:14:31"},"text":"HHH"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"8","sysUser":{"user_division":"分工","createtime":"2017-07-19 14:08:16","department_id":7,"name":"HYK","user_position":"职位","phone_num":"15139464817","login_num":"0","id":8,"user_rank":"01","updatetime":"2017-07-21 11:14:05"},"text":"HYK"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"9","sysUser":{"user_division":"执行","createtime":"2017-07-20 10:05:10","department_id":7,"name":"贾胜飞","user_position":"员工","phone_num":"15737568028","login_num":"0","id":9,"user_rank":"02","updatetime":"2017-07-21 11:04:55"},"text":"贾胜飞"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"10","sysUser":{"user_division":"大总管","createtime":"2017-07-20 10:20:34","department_id":7,"name":"王楷","user_position":"超级管理员","phone_num":"15896559159","login_num":"0","id":10,"user_rank":"02","updatetime":"2017-07-21 11:13:59"},"text":"王楷"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"11","sysUser":{"user_division":"分工","createtime":"2017-07-20 17:43:05","department_id":7,"name":"HH","user_position":"职位","phone_num":"17633905868","id":11,"user_rank":"02","updatetime":"2017-07-21 11:13:54"},"text":"HH"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"12","sysUser":{"user_division":"分工","createtime":"2017-07-20 18:02:20","department_id":7,"name":"测试","user_position":"职位","phone_num":"17633905858","id":12,"user_rank":"02","updatetime":"2017-07-20 18:02:20"},"text":"测试"},{"ChildNodes":[],"parentnodes":"de7","hasChildren":false,"id":"13","sysUser":{"user_division":"分工","createtime":"2017-07-20 18:19:46","department_id":7,"name":"测试2222222","user_position":"职位","phone_num":"15139464444","id":13,"user_rank":"02","updatetime":"2017-07-20 18:24:38"},"text":"测试2222222"}]
     * parentnodes : de5
     * hasChildren : true
     * id : de7
     * text : 科技局
     */

    private String parentnodes;
    private boolean hasChildren;
    private String id;
    private String text;
    private List<ChildNodesBean> ChildNodes;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChildNodesBean> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<ChildNodesBean> ChildNodes) {
        this.ChildNodes = ChildNodes;
    }

    @Override
    public String toString() {
        return "ChildNodesBeanX{" +
                "parentnodes='" + parentnodes + '\'' +
                ", hasChildren=" + hasChildren +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", ChildNodes=" + ChildNodes +
                '}';
    }
}
