package com.haier.cellarette.baselibrary.shuiyin;//LoadFile工具类

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShuiyinLoadFile {

    public static InputStream downLoadFile(String url) throws Exception {
        URL file1 = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) file1.openConnection();
        InputStream input = httpConnection.getInputStream();
        return input;
    }

    /**
     * 将InputStream流转化为File文件对象
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static File copyInputStreamToFile(InputStream inputStream) throws IOException {
        File file = new File(getSDPath(ShuiyinApp.get()) + "tsf.md");//临时读取的文件，要保证路径存在
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 创建跟目录文件
     */
    private String getPath(Context context) {
        String path = getSDPath(context) + "/Luban/image/";//Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 判断高低版本获取跟目录
     */
    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                sdDir = context.getExternalFilesDir(null);
            } else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        } else {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 获得指定元素的子元素中的文本内容
     */

    public static String getAttrText(Element element, String name) {
        NodeList nodeList2 = element.getChildNodes();
        Node node = null;
        for (int i = 0; i < nodeList2.getLength(); i++) {
            node = nodeList2.item(i);
            if (node.getNodeName().equals(name)) {
                return node.getTextContent();
            }
        }
        return null;
    }

    public static List<ShuiyinXmlBean> getNodeList(InputStream inputStream) throws Exception {
        List<ShuiyinXmlBean> mlist = new ArrayList<>();
        //获取DOM解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获DOM解析器
        DocumentBuilder builder = factory.newDocumentBuilder();
        //将解析树放入内存，通过返回值Document来描述结果
        Document document = builder.parse(inputStream);
        //取得根元素<personos>
        Element root = document.getDocumentElement();
        // 得到一个集合，里面存放在xml文件中所有的person
        NodeList nodeList = root.getElementsByTagName("update");
        if (nodeList == null || nodeList.getLength() == 0) {
            return null;
        }
        // 初始化
        for (int i = 0; i < nodeList.getLength(); i++) {
            // xml中的person标签
            Element element = (Element) nodeList.item(i);
            ShuiyinXmlBean p = new ShuiyinXmlBean();
//        // 得到person的id属性
//            int id = Integer.parseInt(element.getAttribute("id"));
//            p.setId(id);
            // 得到name
            String version = getAttrText(element, "version");
            String name = getAttrText(element, "name");
            String content = getAttrText(element, "content");
            String url = getAttrText(element, "url");
            p.setName(version);
            p.setName(name);
            p.setName(content);
            p.setName(url);
//        // 得到age
//            String age = getAttrText(element, "age");
//            p.setAge(new Integer(age));
            mlist.add(p);
        }
        return mlist;
    }


}

