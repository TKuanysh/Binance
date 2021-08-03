package binance.controller;


import io.micrometer.core.instrument.MeterRegistry;

import io.micrometer.core.instrument.Tag;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static binance.common.UrlConstants.CRON_EXP;

@Component
public class DeltaController {
    private final Map<String, String> map_gauge;

    public DeltaController(MeterRegistry meterRegistry) {

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("TagProm","default"));

        map_gauge = meterRegistry.gaugeMapSize("map_gauge_test",tags,new HashMap<>());
    }

    @Scheduled(cron = CRON_EXP)
    public void schedulingTask() {
        System.out.println("Start ... Scheduler" );

        }
}