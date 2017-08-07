package com.example.hasee.yanshi.pojo.NewPojo;

/**
 * Created by hasee on 2017/7/29.
 */

public class JonInfo {
    /**
     * createtime : 2017-07-27
     * id : 53
     * content : <p>如果你的收到此消息，请不要理会，谢谢合作！QAQ如果你的收到此消息，请不要理会，谢谢合作！QAQ如果你的收到此消息，请不要理会，谢谢合作！QAQ如果你的收到此消息，请不要理会，谢谢合作！QAQ如果你的收到此消息，请不要理会，谢谢合作！QAQ</p>
     */

    private String createtime;
    private int id;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "JonInfo{" +
                "createtime='" + createtime + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
