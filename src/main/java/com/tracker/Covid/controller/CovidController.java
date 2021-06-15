package com.tracker.Covid.controller;


import com.tracker.Covid.model.Covid;
import com.tracker.Covid.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CovidController {

    @Autowired
    private CovidDataService covidDataService;

    // display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listCovid", covidDataService.getAllCovid());
        return "covid/index";
    }

    @GetMapping("/showNewCovidForm")
    public String showNewCovidForm(Model model) {
        // create model attribute to bind form data
        Covid covid = new Covid();
        model.addAttribute("covid", covid);
        return "covid/new_covid";
    }

    @PostMapping("/saveCovid")
    public String saveCovid(@ModelAttribute("covid") Covid covid) {
        // save covid to database
        covidDataService.saveCovid(covid);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Covid covid = covidDataService.getCovidById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("covid", covid);
        return "covid/update_covid";
    }

    @GetMapping("/deleteCovid/{id}")
    public String deleteCovid(@PathVariable (value = "id") long id) {
        this.covidDataService.deleteCovidById(id);
        return "redirect:/";
    }

}
