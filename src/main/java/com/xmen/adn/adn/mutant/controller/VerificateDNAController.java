package com.xmen.adn.adn.mutant.controller;

import com.xmen.adn.adn.mutant.request.MutantRequestDTO;
import com.xmen.adn.adn.mutant.service.ProcessDNAServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
@CrossOrigin
@RestController
public class VerificateDNAController {
    @Autowired
    private ProcessDNAServices processDNAServices;
    @PostMapping("/mutant/")
    public ResponseEntity<Boolean> isMutant(@RequestBody MutantRequestDTO body){
    return processDNAServices.isMutant(body.getDna()) ?
            new ResponseEntity<>(true, HttpStatus.OK) :
            new ResponseEntity<>(false, HttpStatus.FORBIDDEN) ;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(){
        return new ResponseEntity<>(processDNAServices.getStats(), HttpStatus.OK);
    }

}
