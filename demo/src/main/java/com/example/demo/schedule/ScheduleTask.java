package com.example.demo.schedule;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.config.SpringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleTask {
    private static Logger logger = Logger.getLogger(ScheduleManage.class);

    private final DefaultManagedTaskScheduler defaultManagedTaskScheduler;

    private final List<ScheduleSetting> scheduleSettingList = new ArrayList<>();

    public ScheduleTask() throws IOException {
        parse();
        defaultManagedTaskScheduler = new DefaultManagedTaskScheduler();
    }

    static final class ScheduleSetting {
        /**
         * 类名
         */
        private String className;

        /**
         * 方法
         */
        private String method;

        /**
         * 参数
         */
        private String parameter;

        /**
         * 初始执行时间
         */
        private int initDelay;

        /**
         * 固定执行周期
         */
        private int fixedDelay;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }

        public int getInitDelay() {
            return initDelay;
        }

        public void setInitDelay(int initDelay) {
            this.initDelay = initDelay;
        }

        public int getFixedDelay() {
            return fixedDelay;
        }

        public void setFixedDelay(int fixedDelay) {
            this.fixedDelay = fixedDelay;
        }
    }

    private void parse() throws IOException {
        File file = ResourceUtils.getFile("classpath:config/schedule.json");
        String json = FileUtils.readFileToString(file, "UTF-8");
        scheduleSettingList.addAll(JSONArray.parseArray(json, ScheduleSetting.class));
    }

    public void start() {
        scheduleSettingList.parallelStream().forEach(this::startScheduleWithFixedDelay);
    }

    private void startScheduleWithFixedDelay(ScheduleSetting setting) {
        Date startTime = new Date(System.currentTimeMillis() + setting.getInitDelay());
        defaultManagedTaskScheduler.scheduleWithFixedDelay(() -> {
            try {
                invoke(setting);
            } catch (Exception e) {
                logger.error("Schedule task start failed! className=[" + setting.getClassName() + "]", e);
            }
        }, startTime, setting.getFixedDelay());
    }

    private void invoke(ScheduleSetting setting) throws Exception {
        Object instance = SpringUtils.getObject(setting.getClassName());
        if (StringUtils.isNotEmpty(setting.getParameter())) {
            Method method = instance.getClass().getDeclaredMethod(setting.getMethod(), String.class);
            method.invoke(instance, setting.getParameter());
        } else {
            Method method = instance.getClass().getDeclaredMethod(setting.getMethod());
            method.invoke(instance);
        }
    }
}
