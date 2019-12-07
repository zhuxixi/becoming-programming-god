package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.exception;

/**
 * 使用特定异常标明当前确实是数据源的问题
 * @author zhuzh
 * @date 2019.10.17
 */
public class DatasourceBusyException extends RuntimeException {
    public DatasourceBusyException(String message) {
        super(message);
    }
}
