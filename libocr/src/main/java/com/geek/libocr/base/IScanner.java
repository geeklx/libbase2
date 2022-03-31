package com.geek.libocr.base;

/**
 * 自定义识别接口
 *
 */
public interface IScanner {

    com.geek.libocr.base.Result scan(byte[] data, int width, int height) throws Exception;

}
