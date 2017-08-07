package com.example.hasee.yanshi.pojo.addressBook;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class ChildNodesBean {
    /**
     * ChildNodes : []
     * parentnodes : de7
     * hasChildren : false
     * id : 7
     * sysUser : {"user_division":"分工","createtime":"2017-07-18 15:10:31","department_id":7,"name":"HHH","user_position":"职位","phone_num":"17633905867","login_num":"0","id":7,"user_rank":"02","updatetime":"2017-07-21 11:14:31"}
     * text : HHH
     */

    private String parentnodes;
    private boolean hasChildren;
    private String id;
    private SysUserBean sysUser;
    private String text;
    private List<?> ChildNodes;

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

    public List<?> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<?> ChildNodes) {
        this.ChildNodes = ChildNodes;
    }

    @Override
    public String toString() {
        return "ChildNodesBean{" +
                "parentnodes='" + parentnodes + '\'' +
                ", hasChildren=" + hasChildren +
                ", id='" + id + '\'' +
                ", sysUser=" + sysUser +
                ", text='" + text + '\'' +
                ", ChildNodes=" + ChildNodes +
                '}';
    }
}
