package com.xmen.adn.adn.mutant.controller;

import com.xmen.adn.adn.mutant.request.MutantRequestDTO;
import com.xmen.adn.adn.mutant.response.StatsDTO;
import com.xmen.adn.adn.mutant.service.ProcessDNAServices;
import com.xmen.adn.adn.mutant.service.impl.ProcessDNAServicesImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class VerificateDNAControllerTest {
    @Autowired
    VerificateDNAController verificateDNAController;
    @MockBean
    private ProcessDNAServices processDNAServices;
    @Test
    void when_is_multiple_calls_isMutant() {
        MutantRequestDTO mutantRequestDTO = new MutantRequestDTO();
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};

        VerificateDNAController mock = Mockito.mock(VerificateDNAController.class);
        for (int i = 1; i < 1000001; i++) {
            mutantRequestDTO.setDna(list);
            mock.isMutant(mutantRequestDTO);
            mutantRequestDTO = new MutantRequestDTO();
        }

        ArgumentCaptor<MutantRequestDTO> argument = ArgumentCaptor.forClass(MutantRequestDTO.class);
        verify(mock, times(1000000)).isMutant(argument.capture());
    }

    @Test
    void when_stats_is_ok() {
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setCountMutantDna(2);
        statsDTO.setCountHumanDna(3);
        statsDTO.setRatio(3d);
        when(processDNAServices.getStats()).thenReturn(statsDTO);
        ResponseEntity<StatsDTO> response = (ResponseEntity<StatsDTO>) verificateDNAController.stats();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void when_mutant_is_ok() {
        MutantRequestDTO mutantRequestDTO = new MutantRequestDTO();
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};
        mutantRequestDTO.setDna(list);
        when(processDNAServices.isMutant(Mockito.any())).thenReturn(true);
        ResponseEntity<Boolean> response = (ResponseEntity<Boolean>) verificateDNAController.isMutant(mutantRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void when_mutant_is_not_ok() {
        MutantRequestDTO mutantRequestDTO = new MutantRequestDTO();
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};
        mutantRequestDTO.setDna(list);
        when(processDNAServices.isMutant(Mockito.any())).thenReturn(false);
        ResponseEntity<Boolean> response = (ResponseEntity<Boolean>) verificateDNAController.isMutant(mutantRequestDTO);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}