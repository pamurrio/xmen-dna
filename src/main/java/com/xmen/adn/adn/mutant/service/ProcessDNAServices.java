package com.xmen.adn.adn.mutant.service;

import com.xmen.adn.adn.mutant.response.StatsDTO;

public interface ProcessDNAServices {
    boolean isMutant(String[] dna);
    StatsDTO getStats();
}
