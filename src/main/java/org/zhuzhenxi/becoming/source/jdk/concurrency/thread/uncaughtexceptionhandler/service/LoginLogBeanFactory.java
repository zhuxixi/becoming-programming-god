package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.service;

import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.dao.LoginLoggingDAO;
import org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.dao.MockLoginLoggingDAOImpl;

/**
 * Bean工厂，用于获取日志记录的DAO
 * @author zhuzh
 * @date 2019.10.17
 */
public class LoginLogBeanFactory {
    private LoginLogBeanFactory(){}
    private static final Object locker = new Object();
    private static LoginLoggingDAO loginLoggingDAO;

    public static LoginLoggingDAO getInstance(){
        if (loginLoggingDAO == null){
            synchronized (locker){
                loginLoggingDAO = new MockLoginLoggingDAOImpl();
            }
        }
        return loginLoggingDAO;
    }

}
