package com.xmen.adn.adn.mutant.service.impl;

import com.xmen.adn.adn.mutant.model.Mutant;
import com.xmen.adn.adn.mutant.repository.MutantRepository;
import com.xmen.adn.adn.mutant.response.StatsDTO;
import com.xmen.adn.adn.mutant.service.ProcessDNAServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessDNAServicesImpl implements ProcessDNAServices {
    @Autowired
    private MutantRepository mutantRepository;

    @Override
    public boolean isMutant(String[] dna) {
        String[] patterns = {
                "AAAA",
                "CCCC",
                "GGGG"
        };
        if (dna.length < 1) {
            return false;
        }
        if (dna[0] == null || dna[0].length() < 0) {
            return false;
        }
        if (dna[0].length() != dna.length) {
            return false;
        }
        String idDna = String.join("", dna);
        Mutant mutant = mutantRepository.findByDna(idDna);
        if(mutant != null)
            return mutant.getMutant();
        mutant = new Mutant();
        if (!findHorizontal(dna, patterns[1])) {
            mutant.setDna(idDna);
            mutant.setMutant(false);
            mutantRepository.save(mutant);
            return false;
        }
        if (!findVertical(dna, patterns[2])) {
            mutant.setDna(idDna);
            mutant.setMutant(false);
            mutantRepository.save(mutant);
            return false;
        }
        if (!findDiagonally(dna, patterns[0])) {
            mutant.setDna(idDna);
            mutant.setMutant(false);
            mutantRepository.save(mutant);
            return false;
        }

        mutant.setDna(idDna);
        mutant.setMutant(true);
        mutantRepository.save(mutant);
        return true;
    }

    @Override
    public StatsDTO getStats() {
        List<Mutant> mutantList = mutantRepository.findAll();
        Long mutantTot = mutantList.stream().filter(Mutant::getMutant).count();
        Long humanTot = mutantList.stream().filter(h -> !h.getMutant()).count();
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setCountHumanDna(humanTot.intValue());
        statsDTO.setCountMutantDna(mutantTot.intValue());
        statsDTO.setRatio(Double.valueOf(mutantTot) / Double.valueOf(humanTot));
        return statsDTO;
    }

    private boolean findHorizontal(String[] dna, String word) {
        if (word.length() > dna.length || word.length() > dna[0].length())
            return false;
        int lengthV = dna.length - 1;
        String horizontalString = dna[lengthV - 1].substring(0, word.length());
        return word.toUpperCase().equals(horizontalString.toUpperCase());
    }

    private boolean findVertical(String[] dna, String word) {
        if (word.length() > dna.length || word.length() > dna[0].length())
            return false;
        int lengthH = dna[0].length() - 1;
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            text.append(dna[i].toCharArray()[lengthH - 1]);
        }
        return word.toUpperCase().equals(text.toString().toUpperCase());
    }

    private boolean findDiagonally(String[] dna, String word) {
        if (word.length() > dna.length || word.length() > dna[0].length())
            return false;
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            text.append(dna[i].toCharArray()[i]);
        }
        return word.toUpperCase().equals(text.toString().toUpperCase());
    }
}
