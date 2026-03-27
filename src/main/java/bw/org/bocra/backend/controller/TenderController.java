package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.Tender;
import bw.org.bocra.backend.repository.TenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenders")
@RequiredArgsConstructor
public class TenderController {

    private final TenderRepository tenderRepository;

    @GetMapping
    public ResponseEntity<List<Tender>> getAllTenders() {
        return ResponseEntity.ok(tenderRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Tender> createTender(@RequestBody Tender tender) {
        return ResponseEntity.ok(tenderRepository.save(tender));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tender> updateTender(@PathVariable Long id, @RequestBody Tender tenderDetails) {
        Tender tender = tenderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tender not found"));
        
        tender.setTenderNumber(tenderDetails.getTenderNumber());
        tender.setTitle(tenderDetails.getTitle());
        tender.setType(tenderDetails.getType());
        tender.setClosingDate(tenderDetails.getClosingDate());
        tender.setStatus(tenderDetails.getStatus());
        tender.setDescription(tenderDetails.getDescription());
        
        return ResponseEntity.ok(tenderRepository.save(tender));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTender(@PathVariable Long id) {
        tenderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
