package com.haier.cellarette.baselibrary.shuiyin

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import cc.shinichi.library.R
import cc.shinichi.library.glide.FileTarget
import cc.shinichi.library.tool.file.FileUtil.Companion.copyFile
import cc.shinichi.library.tool.file.FileUtil.Companion.createFileByDeleteOldFile
import cc.shinichi.library.tool.file.SingleMediaScanner
import cc.shinichi.library.tool.ui.ToastUtil
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.OutputStream

/**
 * @author 工藤
 * @email qinglingou@gmail.com
 * create at 2018/5/4  16:34
 * description:图片下载工具类
 */
object DownloadPictureUtil1 {

    private var onDownloadListener1: OnDownloadListener1? = null
    private var mulu: String? = null

    fun onbackclicklistener(downloadListener1: OnDownloadListener1) {
        this.onDownloadListener1 = onDownloadListener1
    }

    //   shuiyin2_geek    filename_geek
    fun init1(mulu: String, filename1: String): String? {
        //
        this.mulu = mulu
        //创建目录
        val filepath = Utils.getApp()
            .getExternalFilesDir(null)!!.path + File.separator + mulu + File.separator
        val file = File(filepath)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.i("MyPictures", "创建图片存储路径目录失败")
                Log.i("MyPictures", "mediaStorageDir : " + file.path)
            }
        }
        // 创建图片文件
        val filename = "$filename1.jpg"
        val url = filepath + File.separator + filename
        val cropFile = File(url)
        try {
            if (cropFile.exists()) {
                cropFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return url
    }


    fun downloadPicture(context: Activity,currentItem: Int, url: String?) {
        Glide.with(context).downloadOnly().load(url).into(object : FileTarget() {
            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadStart(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(R.string.toast_start_download)
                    )
                }
                super.onLoadStarted(placeholder)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadFailed(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(R.string.toast_save_failed)
                    )
                }
            }

            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                super.onResourceReady(resource, transition)
                save(context, resource, currentItem)
            }
        })
    }

    public fun save(context: Activity, resource: File, currentItem: Int) {
        // 传入的保存文件夹名
//        val downloadFolderName = ImagePreview.instance.folderName
        val downloadFolderName = mulu
        // 保存的图片名称
        var name = System.currentTimeMillis().toString() + ""
        val mimeType = getImageTypeWithMime(resource.absolutePath)
        name = "$name.$mimeType"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 大于等于29版本的保存方法
            val resolver = context.contentResolver
            // 设置文件参数到ContentValues中
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, name)
            values.put(MediaStore.Images.Media.DESCRIPTION, name)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/$mimeType")
            values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/" + downloadFolderName + "/"
            )
            val insertUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            var inputStream: BufferedInputStream? = null
            var os: OutputStream? = null
            try {
                inputStream = BufferedInputStream(FileInputStream(resource.absolutePath))
                os = insertUri?.let { resolver.openOutputStream(it) }
                os?.let {
                    val buffer = ByteArray(1024 * 4)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it } != -1) {
                        os.write(buffer, 0, len)
                    }
                    os.flush()
                }
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadSuccess(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(
                            R.string.toast_save_success,
                            Environment.DIRECTORY_PICTURES + "/" + downloadFolderName
                        )
                    )
                }
                insertUri?.refresh(resolver)
            } catch (e: IOException) {
                e.printStackTrace()
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadFailed(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(R.string.toast_save_failed)
                    )
                }
            } finally {
                try {
                    os?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {
            // 低于29版本的保存方法
            val path = Environment.getExternalStorageDirectory()
                .toString() + "/" + downloadFolderName + "/"
            createFileByDeleteOldFile(path + name)
            val result = copyFile(resource, path, name)
            if (result) {
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadSuccess(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(R.string.toast_save_success, path)
                    )
                }
                SingleMediaScanner(context, path + name, object : SingleMediaScanner.ScanListener {
                    override fun onScanFinish() {
                        // scanning...
                    }
                })
            } else {
                if (onDownloadListener1 != null) {
                    onDownloadListener1?.onDownloadFailed(context, currentItem)
                } else {
                    ToastUtil.instance.showShort(
                        context,
                        context.getString(R.string.toast_save_failed)
                    )
                }
            }
        }
    }

    fun Uri.refresh(
        resolver: ContentResolver,
    ) {
        val imageValues = ContentValues()
        // Android Q添加了IS_PENDING状态，为0时其他应用才可见
        imageValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(this, imageValues, null, null)
    }


    fun getImageTypeWithMime(path: String): String {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        var type = options.outMimeType
        Log.d("DownloadPictureUtil1", "getImageTypeWithMime: path = $path, type1 = $type")
        // ”image/png”、”image/jpeg”、”image/gif”
        type = if (TextUtils.isEmpty(type)) {
            ""
        } else {
            type.substring(6)
        }
        Log.d("DownloadPictureUtil1", "getImageTypeWithMime: path = $path, type2 = $type")
        return type
    }
}