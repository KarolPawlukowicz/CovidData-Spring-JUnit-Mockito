package com.tracker.Covid.component;

import com.tracker.Covid.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;

@Component
public class UpdateCovidData {

    @Autowired
    CovidDataService covidDataService;

   /* @PostConstruct
    public void getAllCovidData() throws InterruptedException, ParseException, IOException {
        covidDataService.fetchAllVirusData();
    }*/
}
