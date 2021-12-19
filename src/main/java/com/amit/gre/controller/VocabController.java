package com.amit.gre.controller;

import com.amit.gre.dto.SearchRequest;
import com.amit.gre.dto.VocabPatchRequest;
import com.amit.gre.entity.Image;
import com.amit.gre.entity.Vocab;
import com.amit.gre.service.VocabService;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    public ResponseEntity<List<Vocab>> search(@RequestBody SearchRequest searchRequest){
        var vocabList = vocabService.searchVocabList(searchRequest);
        return new ResponseEntity<>(vocabList, OK);
    }

    @PostMapping("/vocab/{id}/image")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@PathVariable("id") String id, @RequestParam(value = "image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("please select a picture", OK);
        }

        String fileName = file.getOriginalFilename();
        Image image = Image.builder()
                .name(fileName)
                .createdTime(LocalDateTime.now())
                .content(new Binary(file.getBytes()))
                .contentType(file.getContentType())
                .size(file.getSize()).build();
        vocabService.uploadImage(id, image);

        return new ResponseEntity<>("please select a picture", OK);
    }

}
