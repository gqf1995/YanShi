package com.example.hasee.yanshi.update;

/**
 * Created by johe on 2016/12/29.
 * " versionCode": String 版本号
 "versionUrl":String 版本下载地址
 " updateContent": String 更新内容

 */

public class UpdateMsg  {
    String updateContent;
    /**
     * app_url : http://www.jiashengfei.top:8080/municipal/appversion/yanshi.apk
     * app_version : 2.0
     */

    private String app_url;
    private String app_version;

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }


    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    @Override
    public String toString() {
        return "UpdateMsg{" +
                "updateContent='" + updateContent + '\'' +
                ", app_url='" + app_url + '\'' +
                ", app_version='" + app_version + '\'' +
                '}';
    }
}
