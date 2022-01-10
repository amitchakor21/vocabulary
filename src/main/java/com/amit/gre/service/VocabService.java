package com.amit.gre.service;

import com.amit.gre.dto.SearchRequest;
import com.amit.gre.dto.VocabPatchRequest;
import com.amit.gre.entity.Image;
import com.amit.gre.entity.Vocab;
import com.amit.gre.repository.VocabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class VocabService {

    public static final int DEFAULT_PAGE_SIZE = 40;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    private final VocabRepository vocabRepository;
    private final MongoTemplate mongoTemplate;

    public Vocab updateVocab(String id, VocabPatchRequest vocabRequest) {
        Vocab vocab = vocabRepository.findById(id).orElseThrow();

        BeanUtils.copyProperties(vocabRequest, vocab);
        vocab.setLastUpdated(LocalDateTime.now());
        return vocabRepository.save(vocab);
    }

    public void uploadImage(String id, Image image) {
        var vocab = vocabRepository.findById(id).orElseThrow();
        vocab.setImage(image);
        vocab.setLastUpdated(LocalDateTime.now());
        vocabRepository.save(vocab);
    }

    public List<Vocab> searchVocabList(SearchRequest searchRequest) {
        if (nonNull(searchRequest.getId())) {
            return List.of(vocabRepository.findById(searchRequest.getId()).orElseThrow());
        }

        Query query = new Query();
        if (!isBlank(searchRequest.getWord())) {
            query.addCriteria(Criteria.where("word").regex(searchRequest.getWord(), "i"));
        }
        if (nonNull(searchRequest.getEmotion())) {
            query.addCriteria(Criteria.where("emotion").regex(searchRequest.getEmotion(), "i"));
        }
        if (!isBlank(searchRequest.getScore())) {
            var range = searchRequest.getScore().split("-");
            if (range.length == 1) {
                query.addCriteria(Criteria.where("familiarLevel").gte(Integer.parseInt(range[0])));
            }
            if (range.length == 2) {
                query.addCriteria(
                        Criteria.where("familiarLevel").lte(Integer.parseInt(range[1]))
                                .andOperator(Criteria.where("familiarLevel").gte(Integer.parseInt(range[0])))
                );
            }
        }
        var count = mongoTemplate.count(query, Vocab.class);
        if (nonNull(searchRequest.getPage()) || nonNull(searchRequest.getSize())) {
            int page = searchRequest.getPage() != null ? searchRequest.getPage() : DEFAULT_PAGE_NUMBER;
            int size = searchRequest.getSize() != null ? searchRequest.getSize() : DEFAULT_PAGE_SIZE;

            Pageable pageable = PageRequest.of(page, size);
            query.with(pageable);
            //query.with(Sort.by(Sort.Direction.DESC, "word"));
        }

        var result = mongoTemplate.find(query, Vocab.class);
        result.forEach(vocab -> vocab.setTotal(count));
        return result;
    }
}
