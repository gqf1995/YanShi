package com.example.hasee.yanshi.pojo.addressBook;

/**
 * Created by Administrator on 2017/7/26.
 */

public class SysUserBean {

    /**
     * ref_id : 22
     * user_division : 执行
     * image : /upload/20170718150418.jpg
     * createtime : 2017-07-20 10:05:10
     * department_id : 6
     * user_position : 员工
     * password : 123456
     * user_id : 9
     * role_id : 6
     * name : 贾胜飞
     * phone_num : 15737568028
     * login_num : 0
     * id : 9
     * user_rank : 02
     * updatetime : 2017-08-02 10:51:57
     */

    private int ref_id;
    private String user_division;
    private String image;
    private String createtime;
    private int department_id;
    private String user_position;
    private String password;
    private int user_id;
    private int role_id;
    private String name;
    private String phone_num;
    private String login_num;
    private int id;
    private String user_rank;
    private String updatetime;

    public int getRef_id() {
        return ref_id;
    }

    public void setRef_id(int ref_id) {
        this.ref_id = ref_id;
    }

    public String getUser_division() {
        return user_division;
    }

    public void setUser_division(String user_division) {
        this.user_division = user_division;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "SysUserBean{" +
                "ref_id=" + ref_id +
                ", user_division='" + user_division + '\'' +
                ", image='" + image + '\'' +
                ", createtime='" + createtime + '\'' +
                ", department_id=" + department_id +
                ", user_position='" + user_position + '\'' +
                ", password='" + password + '\'' +
                ", user_id=" + user_id +
                ", role_id=" + role_id +
                ", name='" + name + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", login_num='" + login_num + '\'' +
                ", id=" + id +
                ", user_rank='" + user_rank + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
