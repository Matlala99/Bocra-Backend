package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.Complaint;
import bw.org.bocra.backend.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Transactional
    public Complaint submitComplaint(Complaint complaint) {
        // Generate a secure, unique ticket ID
        complaint.setTicketId("BOCRA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        complaint.setStatus("PENDING");
        return complaintRepository.save(complaint);
    }

    @Transactional(readOnly = true)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Complaint getByTicketId(String ticketId) {
        return complaintRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + ticketId));
    }

    @Transactional
    public Complaint resolveComplaint(Long id, String response) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setAdminResponse(response);
        complaint.setResolutionDate(java.time.LocalDateTime.now());
        complaint.setStatus("RESOLVED");
        return complaintRepository.save(complaint);
    }
}
