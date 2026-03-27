package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.Inquiry;
import bw.org.bocra.backend.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Inquiry saveInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    public java.util.List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    @Transactional
    public Inquiry respondToInquiry(Long id, String response) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setResponseMessage(response);
        inquiry.setRespondedAt(java.time.LocalDateTime.now());
        inquiry.setStatus("RESPONDED");
        return inquiryRepository.save(inquiry);
    }
}
