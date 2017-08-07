package com.example.hasee.yanshi.pojo.Login;

/**
 * Created by Administrator on 2017/7/28.
 */

public class Successful {

    /**
     * code : true
     * user : {"user_division":"分工","password":"123456","createtime":"2017-07-18 15:10:31","department_id":7,"name":"HHH","user_position":"职位","phone_num":"17633905867","login_num":"0","id":7,"user_rank":"02","updatetime":"2017-07-28 09:14:21"}
     */

    private boolean code;
    private UserBean user;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
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

        private String user_division;
        private String password;
        private String createtime;
        private int department_id;
        private String name;
        private String user_position;
        private String phone_num;
        private String login_num;
        private int id;
        private String user_rank;
        private String updatetime;

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
    }
}
