package com.sinosoft.datax_task.task;

import cn.hutool.core.date.DateUtil;
import com.sinosoft.datax_task.util.TaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2022/6/25 10:35
 */
@Slf4j
@Component
public class DataxTask {
    /**
     * 每小时25分同步核酸数据
     */
    @Async
    @Scheduled(cron = "0 25 * * * ?")
    public void execute() {
        try {
            Calendar calendar1h25 = Calendar.getInstance();
            Calendar calendar25 = Calendar.getInstance();

            calendar1h25.add(Calendar.HOUR, -1);
            calendar1h25.add(Calendar.MINUTE, -25);
            calendar25.add(Calendar.MINUTE, -25);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldDate = calendar1h25.getTime();
            String oldString = formatter.format(oldDate);
            String currentString = formatter.format(calendar25.getTime());

            log.info("本次查询日期段：" + oldString + " 至 " + currentString);
            File file = TaskUtil.createLogFile("people_ours_auto_new", calendar1h25, log);

            int monday = 2;
            int dayOfWeek = DateUtil.dayOfWeek(oldDate);
            // 计算偏移量
            int offset = dayOfWeek - monday;
            if (offset < 0) {
                offset = 6;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String ymdStr = simpleDateFormat.format(DateUtil.offsetDay(oldDate, -offset));

            String baseShell = "python datax.py --jvm=\"-Xms3G -Xmx6G\" people_ours_auto_new.json";
            String logPath = "/root/datax/execute_log/";
            String paramShell = String.format(" -p \"-Dymd_str='%s' -Dstart_time='%s' -Dend_time='%s'\" >>%s 2>&1 &", ymdStr, oldString, currentString, logPath + file.getName());
            String[] cmd = new String[]{"/bin/sh", "-c", baseShell + paramShell};
            Runtime rt = Runtime.getRuntime();
            rt.exec(cmd);
        } catch (Exception e) {
            log.error("执行错误:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
