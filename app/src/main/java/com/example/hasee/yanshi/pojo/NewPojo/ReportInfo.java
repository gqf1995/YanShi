package com.example.hasee.yanshi.pojo.NewPojo;

/**
 * Created by hasee on 2017/7/29.
 */

public class ReportInfo {
    /**
     * createtime : 2017-07-28
     * id : 74
     * title : 撒打算
     */

    private String createtime;
    private int id;
    private String title;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "createtime='" + createtime + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
