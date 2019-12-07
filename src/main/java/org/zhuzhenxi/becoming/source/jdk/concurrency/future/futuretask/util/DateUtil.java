package org.zhuzhenxi.becoming.source.jdk.concurrency.future.futuretask.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuzhenxi
 * @date 2019.11.12
 */
public class DateUtil {

    public static String getCurrentTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }
}
