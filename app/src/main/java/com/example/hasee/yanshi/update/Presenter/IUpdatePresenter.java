package com.example.hasee.yanshi.update.Presenter;

import com.example.hasee.yanshi.update.UpdateMsg;

import java.io.File;

/**
 * Created by johe on 2017/3/27.
 */

public interface IUpdatePresenter {

    public void download(String path, UpdateMsg updateMsg);

    public File getOutPutFile();
    public void close();
}
