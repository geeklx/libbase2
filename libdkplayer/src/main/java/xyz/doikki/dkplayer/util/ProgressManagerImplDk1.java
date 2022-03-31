package xyz.doikki.dkplayer.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;

import xyz.doikki.videoplayer.player.ProgressManager;

public class ProgressManagerImplDk1 extends ProgressManager {

    @SuppressLint("LongLogTag")
    @Override
    public void saveProgress(String url, long progress) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (progress == 0) {
            clearSavedProgressByUrl(url);
            return;
        }
        Log.e("VideoPlayerAct-saveProgress", Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0")) + "");
        SPUtils.getInstance().put(String.valueOf(url.hashCode()), progress + "");
    }

    @SuppressLint("LongLogTag")
    @Override
    public long getSavedProgress(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        long a = 0;
        long p = Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0"));
        long max = Long.parseLong(SPUtils.getInstance().getString("xianzhi_times", "0"));
        Log.e("VideoPlayerAct-getSavedProgress-position", Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0")) + "");
        Log.e("VideoPlayerAct-getSavedProgress-max", max + "");
        if (p < max) {
            a = p;
        } else {
            a = max;
        }
        return a;
    }

    public void clearAllSavedProgress() {
        SPUtils.getInstance().clear();
    }

    public void clearSavedProgressByUrl(String url) {
        SPUtils.getInstance().put(String.valueOf(url.hashCode()), 0);
    }
}
