package com.sinosoft.datax_task.util;

import org.slf4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/7/26 15:49
 */
public class TaskUtil {
    public static File createLogFile(String logType, Calendar calendar1d30, Logger logger) throws Exception {
        // 日志时间
        SimpleDateFormat logFormatter = new SimpleDateFormat("yyyyMMdd");
        // 日志文件路径
        String logPathName = "/root/datax/execute_log/" + logType + "_";
        String logFileName = logPathName + logFormatter.format(calendar1d30.getTime()) + ".log";
        File file = new File(logFileName);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new Exception("日志文件创建失败");
            }
            logger.info("日志文件创建完成:" + file.getName());
        }
        return file;
    }
}
