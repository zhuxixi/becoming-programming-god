package org.zhuzhenxi.becoming.source.jdk.concurrency.thread.uncaughtexceptionhandler.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录日志对象，用于记录用户登录信息
 * @author zhuzh
 * @date 2019.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLogPO {
    private int id;
    private String username;
    private String createTime;
}
