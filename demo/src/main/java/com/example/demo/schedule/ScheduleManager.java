package com.example.demo.schedule;

import java.io.IOException;

import org.apache.log4j.Logger;

public class ScheduleManager {
    private static Logger logger = Logger.getLogger(ScheduleManager.class);

    public static void startSchedule() {
        try {
            ScheduleTask scheduleTask = new ScheduleTask();
            scheduleTask.start();
        } catch (IOException e) {
            logger.error("Schedule task start failed!",e);
        }
    }
}
