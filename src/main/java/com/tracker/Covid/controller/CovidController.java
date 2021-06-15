package com.tracker.Covid.controller;

import com.tracker.Covid.model.Covid;
import com.tracker.Covid.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CovidController {
    @Autowired
    private CovidDataService covidDataService;


    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listCovid", covidDataService.getAllCovid());
        return "covid/index";
    }

    @GetMapping("/showNewCovidForm")
    public String showNewCovidForm(Model model) {
        Covid covid = new Covid();
        model.addAttribute("covid", covid);
        return "covid/new_covid";
    }

    @PostMapping("/saveCovid")
    public String saveCovid(@ModelAttribute("covid") Covid covid) {
        covidDataService.saveCovid(covid);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
        Covid covid = covidDataService.getCovidById(id);

        model.addAttribute("covid", covid);
        return "covid/update_covid";
    }

    @GetMapping("/deleteCovid/{id}")
    public String deleteCovid(@PathVariable (value = "id") long id) {
        this.covidDataService.deleteCovidById(id);
        return "redirect:/";
    }
}