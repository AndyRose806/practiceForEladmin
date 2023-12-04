package com.pubo.utils;

/**
 * @Description:
 * @Author:pubo
 * @Date:2023/12/423:12
 */
public interface CallBack {
    /**
     * 回调执行方法
     */
    void executor();

    /**
     * 本回调任务名称
     * @return /
     */
    default String getCallBackName() {
        return Thread.currentThread().getId() + ":" + this.getClass().getName();
    }
}
