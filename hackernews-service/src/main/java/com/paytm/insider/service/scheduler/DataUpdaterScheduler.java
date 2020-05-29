package com.paytm.insider.service.scheduler;

import com.paytm.insider.service.business.DataUpdaterService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Scheduler for collection of Stories, Users and Comments at a time interval
 */
@Component
@EnableScheduling
@Order(4)
public class DataUpdaterScheduler {

    private final DataUpdaterService dataUpdaterService;

    @Autowired
    public DataUpdaterScheduler(DataUpdaterService dataUpdaterService) {
        this.dataUpdaterService = dataUpdaterService;
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    private void update() throws IOException {
        dataUpdaterService.fetchStories();
    }
}
