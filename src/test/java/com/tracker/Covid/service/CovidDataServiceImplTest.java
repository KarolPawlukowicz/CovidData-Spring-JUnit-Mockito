package com.tracker.Covid.service;

import com.tracker.Covid.model.Covid;
import com.tracker.Covid.respository.CovidRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.web.reactive.server.JsonPathAssertions;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CovidDataServiceImplTest {
    @Mock
    private CovidRepository covidRepository;

    @Captor
    private ArgumentCaptor<Covid> covidArgumentCaptor;

    private CovidDataServiceImpl covidDataServiceImpl;

    @BeforeEach
    public void setUp() {
        covidDataServiceImpl = new CovidDataServiceImpl();
    }

    @Test
    @DisplayName("Should save covid data")
    void saveCovidTest() {
        Covid covid = new Covid(1112L, "01-01-2020", 1, 2, 3);

        covidRepository.save(covid);

        Mockito.verify(covidRepository, Mockito.times(1)).save(covidArgumentCaptor.capture());

        Assertions.assertThat(covidArgumentCaptor.getValue().getId()).isEqualTo(1112L);
        Assertions.assertThat(covidArgumentCaptor.getValue().getDate()).isEqualTo(covid.getDate());
        Assertions.assertThat(covidArgumentCaptor.getValue().getDeaths()).isEqualTo(covid.getDeaths());
        Assertions.assertThat(covidArgumentCaptor.getValue().getInfected()).isEqualTo(covid.getInfected());
        Assertions.assertThat(covidArgumentCaptor.getValue().getRecovered()).isEqualTo(covid.getRecovered());
    }

    @Test
    @DisplayName("Should delete covid data")
    void deleteCovidTest() {
        Covid covid = new Covid(1112L, "01-01-2020", 1, 2, 3);

        covidRepository.deleteById(1112L);
        Mockito.verify(covidRepository, Mockito.times(1)).deleteById(1112L);
    }

    @Test
    @DisplayName("Should find covid data by id")
    void getCovidById() {
        Covid expected = new Covid(12L, "01-01-2020", 1, 2, 3);
        Covid actual = null;
        Mockito.when(covidRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));
        Optional<Covid> covid = covidRepository.findById(expected.getId());
        if (covid.isPresent()) {
            actual = covid.get();
        }
        assertEquals("message", expected.getId(), actual.getId());
    }
}