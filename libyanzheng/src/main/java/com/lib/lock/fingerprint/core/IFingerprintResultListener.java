package com.lib.lock.fingerprint.core;

/**
     * 指纹识别回调接口
     */
    public interface IFingerprintResultListener {
        /**
         * 指纹识别成功
         */
        void onAuthenticateSuccess();

        /**
         * 指纹识别失败
         */
        void onAuthenticateFailed(int helpId, String errString);

        /**
         * 指纹识别发生错误-不可短暂恢复
         */
        void onAuthenticateError(int errMsgId);

        /**
         * 开始指纹识别监听成功
         */
        void onStartAuthenticateResult(boolean isSuccess);
    }