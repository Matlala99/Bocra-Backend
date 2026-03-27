package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.LicenseApplication;
import bw.org.bocra.backend.repository.LicenseApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseApplicationRepository licenseApplicationRepository;

    @Transactional
    public LicenseApplication submitApplication(LicenseApplication application) {
        application.setStatus("PENDING");
        return licenseApplicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<LicenseApplication> getAllApplications() {
        return licenseApplicationRepository.findAll();
    }

    @Transactional
    public LicenseApplication updateApplicationStatus(Long id, String status, String adminNotes) {
        LicenseApplication application = licenseApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License application not found"));
        
        application.setStatus(status);
        application.setAdminReviewNotes(adminNotes);
        application.setUpdatedAt(LocalDateTime.now());
        
        return licenseApplicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public LicenseApplication trackApplication(String registrationNumber) {
        return licenseApplicationRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new RuntimeException("No application found with the provided Registration Number"));
    }
}
