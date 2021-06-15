package com.tracker.Covid.controller;

import com.tracker.Covid.model.Covid;
import com.tracker.Covid.respository.CovidRepository;
import com.tracker.Covid.service.CovidDataService;
import com.tracker.Covid.service.CovidDataServiceImpl;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.Assert;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CovidController.class)
public class CovidControllerTest {
    @MockBean
    private CovidDataServiceImpl covidDataService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CovidRepository covidRepository;

    @Test
    @DisplayName("This test should pass when method returns Ok status")
    void getCovidListReturnOk() throws Exception {
        Covid covid1 = new Covid(1L, "01-01-2020", 69, 69, 69);
        Covid covid2 = new Covid(2L, "01-02-2020", 79, 79, 79);
        when(covidDataService.getAllCovid()).thenReturn(asList(covid1, covid2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("This test should pass when method returns list of 2 covid data objects")
    void getCovidListReturnListOfTwoCovidData() throws Exception {
        Covid covid1 = new Covid(1L, "01-01-2020", 69, 69, 69);
        Covid covid2 = new Covid(2L, "01-02-2020", 79, 79, 79);
        when(covidDataService.getAllCovid()).thenReturn(asList(covid1, covid2));

        mockMvc.perform(get("/"))
                .andExpect(model().attribute("listCovid", hasSize(2)));
    }

    @Test
    @DisplayName("This test should pass when method returns covid data object with id=1, infected, deaths, recoveret = 69 and date 01-01-2020")
    void getCovidListReturnCovidDataWithSpecificParameters() throws Exception {
        Covid covid1 = new Covid(1L, "01-01-2020", 69, 69, 69);
        when(covidDataService.getAllCovid()).thenReturn(asList(covid1));

        mockMvc.perform(get("/"))
                .andExpect(model().attribute("listCovid", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("date", (Matcher<?>) is("01-01-2020")),
                                hasProperty("infected", is(69L)),
                                hasProperty("deaths", is(69L)),
                                hasProperty("recovered", is(69L))
                        )
                )));
    }

    @Disabled
    @Test
    @DisplayName("Should return 2 covid objects with date 01-01-2020 and 01-02-2020")
    void getCovidList() throws Exception {
        Covid covid1 = new Covid(1L, "01-01-2020", 69, 69, 69);
        Covid covid2 = new Covid(2L, "01-02-2020", 79, 79, 79);
        when(covidDataService.getAllCovid()).thenReturn(asList(covid1, covid2));

        MvcResult result = mockMvc.perform(get("/"))
               // .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("listCovid", notNullValue()))
                .andExpect(view().name("covid/index"))
                //.andExpect(model().attribute("listCovid", Matchers.hasItemInArray(Matchers.<Covid> hasProperty("date", Matchers.equalToIgnoringCase("01-01-2020")))))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("01-01-2020"));
        assertTrue(content.contains("01-02-2020"));
        assertTrue(content.contains("69"));
        //assertFalse(content.contains("01-03-2020"));
       // System.out.println("Odpowiedz: " + content);
    }
}