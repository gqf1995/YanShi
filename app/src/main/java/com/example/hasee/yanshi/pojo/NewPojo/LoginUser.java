package com.example.hasee.yanshi.pojo.NewPojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hasee on 2017/7/29.
 */

public class LoginUser extends RealmObject {

    /**
     * user_division : 分工
     * password : 123456
     * createtime : 2017-07-18 15:10:31
     * department_id : 7
     * name : HHH
     * user_position : 职位
     * phone_num : 17633905867
     * login_num : 0
     * id : 7
     * user_rank : 02
     * updatetime : 2017-07-28 09:14:21
     */

    @PrimaryKey
    private int id;
    private String image;
    private String user_division;
    private String password;
    private String createtime;
    private int department_id;
    private String name;
    private String user_position;
    private String phone_num;
    private String login_num;

    private String user_rank;
    private String updatetime;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_division() {
        return user_division;
    }

    public void setUser_division(String user_division) {
        this.user_division = user_division;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLogin_num() {
        return login_num;
    }

    public void setLogin_num(String login_num) {
        this.login_num = login_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", user_division='" + user_division + '\'' +
                ", password='" + password + '\'' +
                ", createtime='" + createtime + '\'' +
                ", department_id=" + department_id +
                ", name='" + name + '\'' +
                ", user_position='" + user_position + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", login_num='" + login_num + '\'' +
                ", user_rank='" + user_rank + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
