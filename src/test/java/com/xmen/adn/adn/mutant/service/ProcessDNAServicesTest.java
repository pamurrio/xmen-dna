package com.xmen.adn.adn.mutant.service;

import com.xmen.adn.adn.mutant.service.impl.ProcessDNAServicesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessDNAServicesTest {
    @Autowired
    ProcessDNAServicesImpl processDNAServices;
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
                "CCCCAT",
                "CTAGTC"};
        boolean value = processDNAServices.isMutant(list);
        Assertions.assertFalse(value);
    }

}
