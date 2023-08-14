package com.geek.libskin.skindemo;

import android.content.Context;

import com.geek.libskin.skinbase.util.BaseApp3;
import com.geek.libskin.skinbase.util.FitAndroidAssetsToCacheUtil3;

import java.io.File;

/**
 * @ClassName: Config
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 2:41 PM$
 * TODO
 */
public class SkinConfig {

    // 文件
//    private static final String SKIN_PACK = "skin-pack-making-debug.apk";
    private static final String SKIN_PACK = "skin.apk";

    // 文件夹
//    private static final String FOLDER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    private static final String FOLDER_PATH = BaseApp3.get().getExternalFilesDir(null).getAbsolutePath() + File.separator;

    public static final String PATH = FOLDER_PATH + SKIN_PACK;

    public static String getPath(Context context) {
        //
        // 文件bufen  INSTALLAPK
        String file_apk_name = SKIN_PACK;
//        String file_apk_name = "山东视频会议_20210314.apk";
        String file_apk_assets_lujing = "apks/";
        File file_apk = FitAndroidAssetsToCacheUtil3.getAssetsHuanCunLujing(context, file_apk_name, file_apk_assets_lujing);
//        return PATH;
        return file_apk.getAbsolutePath();
    }
}
