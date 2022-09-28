package com.amit.gre.repository;

import com.amit.gre.entity.Vocab;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VocabRepository extends MongoRepository<Vocab, String> {
}
