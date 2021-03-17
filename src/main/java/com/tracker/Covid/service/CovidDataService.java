package com.tracker.Covid.service;

import com.tracker.Covid.model.Covid;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CovidDataService {
    List<Covid> getAllCovid();
    void saveCovid(Covid covid);
    Covid getCovidById(long id);
    void deleteCovidById(long id);
    Page<Covid> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    void fetchAllVirusData() throws IOException, InterruptedException, ParseException;

}
