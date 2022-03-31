package com.geek.libocr.bankcard;

public class BankCardInfo {

    static {
        System.loadLibrary("BankCardInfo");
    }

    public native static String bankCardInfo(String cardNumber);

}
