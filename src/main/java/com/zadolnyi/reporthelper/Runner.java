package com.zadolnyi.reporthelper;

import com.zadolnyi.reporthelper.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {
    @Autowired
    ReporterService sendService;

    @Override public void run (ApplicationArguments applicationArguments) throws Exception {
        sendService.start();
    }
}