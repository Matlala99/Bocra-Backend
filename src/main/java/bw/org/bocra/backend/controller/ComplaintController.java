package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.Complaint;
import bw.org.bocra.backend.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // Public Endpoint: Submitting a complaint
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitComplaint(@Valid @RequestBody Complaint complaint) {
        Complaint savedComplaint = complaintService.submitComplaint(complaint);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("ticketId", savedComplaint.getTicketId());
        response.put("message", "Complaint submitted successfully.");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Secured Endpoint: Admin viewing all complaints (Requires JWT)
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // Public Endpoint (Optionally Secured): Checking status
    @GetMapping("/{ticketId}")
    public ResponseEntity<Complaint> getComplaintStatus(@PathVariable String ticketId) {
        return ResponseEntity.ok(complaintService.getByTicketId(ticketId));
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Complaint> resolveComplaint(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String response = body.get("response");
        return ResponseEntity.ok(complaintService.resolveComplaint(id, response));
    }
}
