package com.example.hasee.yanshi.update;

/**
 * Created by johe on 2016/12/29.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
