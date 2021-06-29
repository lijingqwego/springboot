package com.example.demo;

import com.example.demo.schedule.ScheduleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ScheduleManager.startSchedule();
        SpringApplication.run(App.class, args);
    }

}
