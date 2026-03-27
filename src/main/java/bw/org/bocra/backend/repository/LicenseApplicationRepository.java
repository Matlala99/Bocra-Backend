package bw.org.bocra.backend.repository;

import bw.org.bocra.backend.model.LicenseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseApplicationRepository extends JpaRepository<LicenseApplication, Long> {
    List<LicenseApplication> findByStatus(String status);
    List<LicenseApplication> findByApplicantNameContainingIgnoreCase(String name);
    java.util.Optional<LicenseApplication> findByRegistrationNumber(String registrationNumber);
}
