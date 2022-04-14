package com.amit.gre.controller;

import com.amit.gre.dto.SearchRequest;
import com.amit.gre.dto.VocabPatchRequest;
import com.amit.gre.entity.Vocab;
import com.amit.gre.service.VocabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class VocabController {

    private final VocabService vocabService;

    @PatchMapping("/id/{id}")
    public ResponseEntity<Vocab> patchVocab(@PathVariable("id") String id, @RequestBody VocabPatchRequest vocab) {
        var updatedVocab = vocabService.updateVocab(id, vocab);
        return new ResponseEntity<>(updatedVocab, OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Vocab>> search(@RequestBody SearchRequest searchRequest) {
        var vocabList = vocabService.searchVocabList(searchRequest);
        return new ResponseEntity<>(vocabList, OK);
    }

}
