package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.Document;
import bw.org.bocra.backend.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments(@RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(documentRepository.findByCategory(category));
        }
        return ResponseEntity.ok(documentRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document doc) {
        return ResponseEntity.ok(documentRepository.save(doc));
    }
}
