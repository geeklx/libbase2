package com.haier.cellarette.baselibrary.shuiyin;

import android.app.Activity;

public interface OnDownloadListener1 {
    void onDownloadStart(Activity activity,int position);
    void onDownloadSuccess(Activity activity,int position);
    void onDownloadFailed(Activity activity,int position);
};
