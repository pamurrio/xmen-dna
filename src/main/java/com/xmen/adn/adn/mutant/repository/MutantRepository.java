package com.xmen.adn.adn.mutant.repository;

import com.xmen.adn.adn.mutant.model.Mutant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MutantRepository extends MongoRepository<Mutant, String> {
    Mutant findByDna(String Dna);
}
