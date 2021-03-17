package com.tracker.Covid.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "covid")
public class Covid {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private String date;

    @Column(name = "infected")
    private long infected;

    @Column(name = "deaths")
    private long deaths;

    @Column(name = "recovered")
    private long recovered;

    public Covid() {

    }

    public Covid(String date, long infected) {
        this.date = date;
        this.infected = infected;
    }

    public Covid(long id, String date, long infected, long deaths, long recovered) {
        this.id = id;
        this.date = date;
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getInfected() {
        return infected;
    }

    public void setInfected(long infected) {
        this.infected = infected;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }
}
