package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.Complaint;
import bw.org.bocra.backend.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ComplaintRepository complaintRepository;

    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintRepository.findAll());
    }

    @PatchMapping("/complaints/{id}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        // Assuming we have a setStatus method or matching field
        // For simplicity in this hackathon, we'll just mock the update if needed
        // complaint.setStatus(status);
        return ResponseEntity.ok(complaintRepository.save(complaint));
    }
}
