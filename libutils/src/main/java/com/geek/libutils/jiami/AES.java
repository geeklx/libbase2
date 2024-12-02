package com.geek.libutils.jiami;

import android.os.Build;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.SPUtils;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 对称加密算法，加解密工具类
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AES {

    private static final String TAG = AES.class.getSimpleName() + " --> ";

    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * AES 的 密钥长度，32 字节，范围：16 - 32 字节
     */
    public static final int SECRET_KEY_LENGTH = 32;

    /**
     * 字符编码
     */
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * 秘钥长度不足 16 个字节时，默认填充位数
     */
    private static final String DEFAULT_VALUE = "0";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_ALGORITHM2 = "AES/CBC/PKCS5Padding";
    /**
     * 加密密码，长度：16 或 32 个字符(随便定义)
     */
    public static final String secretKey = "MkszczNBNTI2MWE3MjRwNQ==";//   qweasd123
    public static final String secretKeyIndex = "MjVwODJTOTRrNTRFNjI2NQ==";//   qweasd123
    public static final byte[] secretKeyIndexByte = new byte[]{50, 53, 112, 56, 50, 83, 57, 52, 107, 53, 52, 69, 54, 50, 54, 53};
    public static String DOWNLOAD_PATH = "sparkenglish";

    /**
     * AES 加密
     *
     * @param data 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String data) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //初始化为加密密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));
            byte[] encryptByte = cipher.doFinal(data.getBytes(CHARSET_UTF8));
            // 将加密以后的数据进行 Base64 编码
            return base64Encode(encryptByte);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param base64Data 加密的密文 Base64 字符串
     */
    public static String decrypt(String base64Data) {
        try {
            byte[] data = base64Decode(base64Data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey));
            //执行解密操作
            byte[] result = cipher.doFinal(data);
            return new String(result, CHARSET_UTF8);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public static String aesEncrypt(String base64Data) {
        String secretKey11 = SPUtils.getInstance().getString("secretKey", secretKey);
        String secretKeyIndex11 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
//        secretKey1 = "2K3s3A5261a724p5";
//        secretKeyIndex1 = "25p82S94k54E6265";
        String secretKey1 = new String(base64Decode(secretKey11), CHARSET_UTF8);
        String secretKeyIndex1 = new String(base64Decode(secretKeyIndex11), CHARSET_UTF8);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM2);
//            SecretKeySpec skeySpec = getSecretKey(secretKey1);
//            SecretKeySpec skeySpec = new SecretKeySpec(secretKey1.getBytes(), KeyProperties.KEY_ALGORITHM_AES);
            SecretKeySpec skeySpec = new SecretKeySpec(base64Decode(secretKey11), KeyProperties.KEY_ALGORITHM_AES);
//            IvParameterSpec iv = new IvParameterSpec(secretKeyIndex1.getBytes());
            IvParameterSpec iv = new IvParameterSpec(base64Decode(secretKeyIndex11));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encryptByte = cipher.doFinal(base64Data.getBytes(CHARSET_UTF8));
            // 将加密以后的数据进行 Base64 编码
            return base64Encode(encryptByte);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String aesDecrypt(String base64Data) {
        String secretKey11 = SPUtils.getInstance().getString("secretKey", secretKey);
        String secretKeyIndex11 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
//        secretKey1 = "2K3s3A5261a724p5";
//        secretKeyIndex1 = "25p82S94k54E6265";
        String secretKey1 = new String(base64Decode(secretKey11), CHARSET_UTF8);
        String secretKeyIndex1 = new String(base64Decode(secretKeyIndex11), CHARSET_UTF8);
        try {
            byte[] data = base64Decode(base64Data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM2);
//            SecretKeySpec skeySpec = new SecretKeySpec(secretKey1.getBytes(), KeyProperties.KEY_ALGORITHM_AES);
            SecretKeySpec skeySpec = new SecretKeySpec(base64Decode(secretKey11), KeyProperties.KEY_ALGORITHM_AES);
//            IvParameterSpec iv = new IvParameterSpec(secretKeyIndex1.getBytes());
            IvParameterSpec iv = new IvParameterSpec(base64Decode(secretKeyIndex11));
            //设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //执行解密操作
            byte[] result = cipher.doFinal(data);
            return new String(result, CHARSET_UTF8);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public static String encrypt2(String data) {
        String secretKey1 = SPUtils.getInstance().getString("secretKey", secretKey);
        String secretKeyIndex1 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey1.getBytes(), KeyProperties.KEY_ALGORITHM_AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(secretKeyIndex1.getBytes());
//            IvParameterSpec iv = new IvParameterSpec(base64Decode(secretKeyIndex1));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM2);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return base64Encode(encrypted);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public static String decrypt2(String value) {
        try {
            String secretKey1 = SPUtils.getInstance().getString("secretKey", secretKey);
            String secretKeyIndex1 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM2);
            SecretKeySpec secretKey = new SecretKeySpec(secretKey1.getBytes(), KeyProperties.KEY_ALGORITHM_AES);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(secretKeyIndex1.getBytes());
//            IvParameterSpec iv = new IvParameterSpec(base64Decode(secretKeyIndex1));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] original = cipher.doFinal(base64Decode(value));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 使用密码获取 AES 秘钥
     */
    public static SecretKeySpec getSecretKey(String secretKey) {
        secretKey = toMakeKey(secretKey, SECRET_KEY_LENGTH, DEFAULT_VALUE);
        return new SecretKeySpec(secretKey.getBytes(CHARSET_UTF8), KEY_ALGORITHM);
    }

    /**
     * 如果 AES 的密钥小于 {@code length} 的长度，就对秘钥进行补位，保证秘钥安全。
     *
     * @param secretKey 密钥 key
     * @param length    密钥应有的长度
     * @param text      默认补的文本
     * @return 密钥
     */
    private static String toMakeKey(String secretKey, int length, String text) {
        // 获取密钥长度
        int strLen = secretKey.length();
        // 判断长度是否小于应有的长度
        if (strLen < length) {
            // 补全位数
            StringBuilder builder = new StringBuilder();
            // 将key添加至builder中
            builder.append(secretKey);
            // 遍历添加默认文本
            for (int i = 0; i < length - strLen; i++) {
                builder.append(text);
            }
            // 赋值
            secretKey = builder.toString();
        }
        return secretKey;
    }

    /**
     * 将 Base64 字符串 解码成 字节数组
     */
    public static byte[] base64Decode(String data) {
        return Base64.decode(data, Base64.NO_WRAP);
    }

    /**
     * 将 字节数组 转换成 Base64 编码
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /**
     * 处理异常
     */
    private static void handleException(Exception e) {
        e.printStackTrace();
        Log.e(TAG, TAG + e);
    }

    /**
     * 对文件进行AES加密
     *
     * @param sourceFile 待加密文件
     * @param dir        加密后的文件存储路径
     * @param toFileName 加密后的文件名称
     * @return 加密后的文件
     * @paramsecretKey 密钥
     */
    public static File encryptFile(File sourceFile, String dir, String toFileName) {
        String secretKey1 = SPUtils.getInstance().getString("secretKey", secretKey);
        String secretKeyIndex1 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
        String secretKeyIndexByte1 = SPUtils.getInstance().getString("secretKeyIndexByte", base64Encode(secretKeyIndexByte));
        try {
            //
            File tempDir = new File(BaseApp.get().getExternalFilesDir(null), DOWNLOAD_PATH);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            // 创建加密后的文件
            File encryptFile = new File(dir, toFileName);
            if (!encryptFile.exists()) {
                encryptFile.createNewFile();
            }
            // 根据文件创建输出流
            FileOutputStream outputStream = new FileOutputStream(encryptFile);
            // 初始化 Cipher
            Cipher cipher = initFileAESCipher(secretKey1, secretKeyIndex1, base64Decode(secretKeyIndexByte1), Cipher.ENCRYPT_MODE);
            // 以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(sourceFile), cipher);
            // 创建缓存字节数组
            byte[] buffer = new byte[1024 * 2];
            // 读取
            int len;
            // 读取加密并写入文件
            while ((len = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            // 关闭加密输入流
            cipherInputStream.close();
            closeStream(outputStream);
            return encryptFile;
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * AES解密文件
     *
     * @param sourceFile 源加密文件
     * @param dir        解密后的文件存储路径
     * @param toFileName 解密后的文件名称
     * @paramsecretKey 密钥
     */
    public static File decryptFile(File sourceFile, String dir, String toFileName) {
        String secretKey1 = SPUtils.getInstance().getString("secretKey", secretKey);
        String secretKeyIndex1 = SPUtils.getInstance().getString("secretKeyIndex", secretKeyIndex);
        String secretKeyIndexByte1 = SPUtils.getInstance().getString("secretKeyIndexByte", base64Encode(secretKeyIndexByte));
        try {
            //
//            File tempDir = DownloadManager.downloadPath1;
//            File tempDir = new File(BaseApp.get().getExternalFilesDir(null), DOWNLOAD_PATH + "/temp/");
            File tempDir = new File("/data/data/" + BaseApp.get().getPackageName() + "/cache/");// 沙盒内
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            // 创建解密文件
            File decryptFile = new File(dir, toFileName);
            if (!decryptFile.exists()) {
                decryptFile.createNewFile();
            }
            // 初始化Cipher
            Cipher cipher = initFileAESCipher(secretKey1, secretKeyIndex1, base64Decode(secretKeyIndexByte1), Cipher.DECRYPT_MODE);
            // 根据源文件创建输入流
            FileInputStream inputStream = new FileInputStream(sourceFile);
            // 获取解密输出流
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(decryptFile), cipher);
            // 创建缓冲字节数组
            byte[] buffer = new byte[1024 * 2];
            int len;
            // 读取解密并写入
            while ((len = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
            }
            // 关闭流
            cipherOutputStream.close();
            closeStream(inputStream);
            return decryptFile;
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }

    // 检查视频是否使用AES加密
    public static boolean isVideoEncryptedWithAES(String videoPath) {
        File videoFile = new File(videoPath);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(videoFile, "r");
            FileChannel channel = raf.getChannel();

            // 尝试从文件开始读取32字节，以查找'moov' box
            ByteBuffer buffer = ByteBuffer.allocate(32);
            buffer.order(ByteOrder.BIG_ENDIAN);
            channel.read(buffer);
            buffer.flip();

            byte[] header = new byte[4];
            buffer.get(header);

            // 检查是否为'moov'
            return !(header[0] == 'm' && header[1] == 'o' && header[2] == 'o' && header[3] == 'v');
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化 AES Cipher
     *
     * @param secretKey  密钥
     * @param cipherMode 加密模式
     * @return 密钥
     */
    private static Cipher initFileAESCipher(String secretKey, String secretKeyIndex, byte[] secretKeyIndex2, int cipherMode) {
        try {
            // 创建密钥规格
//            SecretKeySpec secretKeySpec = getSecretKey(secretKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(base64Decode(secretKey), KeyProperties.KEY_ALGORITHM_AES);
            // 获取密钥
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM2);
            // 初始化
//            int aaa = secretKeyIndex.getBytes(CHARSET_UTF8).length;
//            cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(base64Decode(secretKeyIndex)));
//            cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(secretKeyIndex2));
            return cipher;
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public static IvParameterSpec generateIV() {
        byte[] iv = new byte[16]; // 16字节长度的数组
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv); // 生成随机的16字节
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        String aaa = ConvertUtils.bytes2String(iv);
        String aaa = base64Encode(iv);
        byte[] bbb = base64Decode(secretKeyIndex);
        int aaaa = aaa.getBytes(CHARSET_UTF8).length;
        String aaaaaa = "https://img.sparke.cn/images/0c7ed92db3a148e3af38b5f9877c4actop.png";
        String key1 = "2K3s3A5261a724p5";
        String iv1 = "25p82S94k54E6265";
        String cccc = aesEncrypt(aaaaaa);
        String dddd = aesDecrypt(cccc);
        byte[] eeeee = base64Decode("8zM51IrH+51dqccP3oKBAw==");
        byte[] eeeee2 = base64Decode("MLWFLnhomZTR6pwLA10QWA==");
        String a1 = aesDecrypt("EcJkI3AzTGYtOXoYxf0xsAkIlG6HYO9ZFWkkgfpKYQmBw1v20u18cJ9jkyvoKvcL+vAn1py4L5VFfsJEUHNdTXIi9yacmc5wRl5g2uBxZuxvS4sbVAyEFp4jd67Oz91QsgUjQrTY9YsFzcnUvPgo4d0kHc+GLKyMvMqkxdcAycovgJY/JFz3xXUVLaaO7u22ZyPnm18akPgaDJMHzgXdHQNtZGibL++cndytfJ4QgfVc0rh0c4g9GC1hVRpMrnJJKQf69uIUqRjSE3BzuJBCAQ==");
        MyLogUtil.e(a1);
        return ivSpec;
    }

    /**
     * 关闭流
     *
     * @param closeable 实现Closeable接口
     */
    private static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
}