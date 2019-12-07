package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.dao;

import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.po.LoginLogPO;

/**
 * 登录日志持久层
 * @author zhuzh
 * @date 2019.10.17
 */
public interface LoginLoggingDAO {
    /**
     * 记录登录日志到数据库
     * @param po
     * @return
     */
    boolean log(LoginLogPO po);

    /**
     * 记录日志失败
     * @return
     */
    void logFailed();

    /**
     * 获取备用数据源中的日志数据量
     * @return
     */
    int getBackupDatasourceSize();
}
