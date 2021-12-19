package com.amit.gre.repository;

import com.amit.gre.entity.Vocab;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VocabRepository extends MongoRepository<Vocab, String> {
    List<Vocab> findByWordIsLike(String word);

}
