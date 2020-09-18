package com.xmen.adn.adn.mutant.service;

import com.xmen.adn.adn.mutant.model.Mutant;
import com.xmen.adn.adn.mutant.repository.MutantRepository;
import com.xmen.adn.adn.mutant.response.StatsDTO;
import com.xmen.adn.adn.mutant.service.impl.ProcessDNAServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProcessDNAServicesTest {
    @Autowired
    ProcessDNAServicesImpl processDNAServices;
    @MockBean
    private MutantRepository mutantRepository;
    @Test
    void when_Searching_And_IsMutant_Then_Return_True(){
        String[] list = {
                        "ATCAGA",
                        "AACEGG",
                        "GAAEGT",
                        "TTAAGC",
                        "CCCCAT",
                        "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertTrue(value);
    }

    @Test
    void when_Searching_And_IsNotMutant_Then_Return_False(){
        String[] list = {
                "ATCAGA",
                "ACCEGG",
                "GAGEGT",
                "TTAGGC",
                "CCCCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertFalse(value);
    }

    @Test
    void when_Searching_Diagonally_And_Exist_Then_Return_True(){
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertTrue(value);
    }

    @Test
    void when_Searching_Horizontally_And_Not_Exist_Then_Return_False(){
        String[] list = {
                "ATCAGA",
                "ACCETG",
                "GAGEET",
                "TTAGGC",
                "CCCCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertFalse(value);
    }

    @Test
    void when_Searching_Vertically_And_Exist_Then_Return_False(){
        String[] list = {
                "ATCAGA",
                "ACCEGG",
                "GAGEGT",
                "TTAGGC",
                "CCCCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertFalse(value);
    }

    @Test
    void when_Searching_Diagonally_And_Exist_Then_Return_False(){
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CACCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertFalse(value);
    }

    @Test
    void when_Exist_ADN_in_DB_Then_Return_isMutant(){
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};
        Mutant mutant = new Mutant();
        mutant.setMutant(true);
        when(mutantRepository.findByDna(Mockito.any())).thenReturn(mutant);
        boolean isMutant = processDNAServices.isMutant(list);
        Assertions.assertTrue(isMutant);
    }

    @Test
    void when_NotExist_ADN_in_DB_Then_Return_isMutant(){
        String[] list = {
                "ATCAGA",
                "AACEGG",
                "GAAEGT",
                "TTAAGC",
                "CCCCAT",
                "CTAGTC"};
        Mutant mutant = new Mutant();
        mutant.setMutant(true);
        when(mutantRepository.findByDna(Mockito.any())).thenReturn(null);
        //doNothing().when(mutantRepository.save(mutant));
        boolean isMutant = processDNAServices.isMutant(list);
        Assertions.assertTrue(isMutant);
    }

    @Test
    void when_is_empty_list_Then_Return_false(){
        String[] list = {};
        boolean isMutant = processDNAServices.isMutant(list);
        Assertions.assertFalse(isMutant);
        String[] list2 = {"sdfff","ff"};
        isMutant = processDNAServices.isMutant(list2);
        Assertions.assertFalse(isMutant);
        String[] list3 = {"",""};
        isMutant = processDNAServices.isMutant(list3);
        Assertions.assertFalse(isMutant);
        String[] list4 = {null};
        isMutant = processDNAServices.isMutant(list4);
        Assertions.assertFalse(isMutant);
    }


    @Test
    void when_invoque_stats_then_return_StatsDTO(){
        Mutant mutant1 = new Mutant();
        Mutant human1 = new Mutant();
        mutant1.setMutant(true);
        human1.setMutant(false);
        List<Mutant> list = Arrays.asList(mutant1,human1);
        when(mutantRepository.findAll()).thenReturn(list);
        StatsDTO statsDTO = processDNAServices.getStats();
        Assertions.assertEquals(1, statsDTO.getRatio());
    }
}
