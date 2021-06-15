package com.tracker.Covid.service;

import com.tracker.Covid.model.Covid;
import com.tracker.Covid.respository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Service
public class CovidDataServiceImpl implements CovidDataService{

    @Autowired
    private CovidRepository covidRepository;

    @Override
    public List<Covid> getAllCovid() {
        return covidRepository.findAll();
    }

    @Override
    public Covid getCovidById(long id) {
        Optional<Covid> optional = covidRepository.findById(id);
        Covid covid = null;
        if (optional.isPresent()) {
            covid = optional.get();
        } else {
            throw new RuntimeException(" Covid data not found for id :: " + id);
        }
        return covid;
    }

    @Override
    public void deleteCovidById(long id) {
        this.covidRepository.deleteById(id);
    }

    @Override
    public void saveCovid(Covid covid) {
        this.covidRepository.save(covid);
    }

    @Override
    public Page<Covid> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.covidRepository.findAll(pageable);
    }






    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    @Override
    public void fetchAllVirusData() throws IOException, InterruptedException, ParseException {
        System.out.println(covidRepository.findAll().size());
        if(covidRepository.findAll().size() == 0) {
            String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
            List<Covid> covidDataList = new ArrayList<>();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(VIRUS_DATA_URL))
                    .build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
           // System.out.println(httpResponse.body());
            StringReader csvBodyReader = new StringReader(httpResponse.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
           // System.out.println(records);
            for (CSVRecord record : records) {
                if (record.get("Country/Region").equals("Poland")){
                    System.out.println(record.get("Country/Region"));
                    System.out.println(record.get(record.size() - 1));
                    int i = 0;
                    for (Map.Entry<String, String> column : record.toMap().entrySet()) {
                       // CovidData covidData = new CovidData();
                        if(i > 3) {
                           // covidData.date = column.getKey();
                           // covidData.infected = Long.parseLong(column.getValue());
                            covidDataList.add(new Covid(column.getKey(), Long.parseLong(column.getValue())));
                           // System.out.println(column.getKey() + "=" + column.getValue());
                        }
                        i++;
                    }
                }
            }


            ///////////////////////////////////////////////////////////////
            VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(VIRUS_DATA_URL))
                    .build();
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(httpResponse.body());
            csvBodyReader = new StringReader(httpResponse.body());
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

            for (CSVRecord record : records) {
                if (record.get("Country/Region").equals("Poland")){
                    System.out.println(record.get("Country/Region"));
                    System.out.println(record.get(record.size() - 1));
                    int i = 0;
                    for (Map.Entry<String, String> column : record.toMap().entrySet()) {
                        if(i > 3) {
                            covidDataList.get(i-4).setDeaths(Long.parseLong(column.getValue()));
                            //System.out.println(column.getKey() + "=" + column.getValue());
                        }
                        i++;
                    }
                }
            }


            //////////////////////////////////////////////////
            VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(VIRUS_DATA_URL))
                    .build();
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(httpResponse.body());
            csvBodyReader = new StringReader(httpResponse.body());
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

            for (CSVRecord record : records) {
                if (record.get("Country/Region").equals("Poland")){
                    System.out.println(record.get("Country/Region"));
                    System.out.println(record.get(record.size() - 1));
                    int i = 0;
                    for (Map.Entry<String, String> column : record.toMap().entrySet()) {
                        if(i > 3) {
                            covidDataList.get(i-4).setRecovered(Long.parseLong(column.getValue()));
                            //System.out.println(column.getKey() + "=" + column.getValue());
                        }
                        i++;
                    }
                }
            }


            for (Covid cd : covidDataList) {
                //System.out.println(cd.date + " - " + cd.infected + " - " + cd.deaths + " - " + cd.recovered);
                this.covidRepository.save(cd);
            }
        }

    }

}
