package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "license_applications")
public class LicenseApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Applicant name is required")
    private String applicantName;

    @NotBlank(message = "ID or Company Registration is required")
    @Convert(converter = bw.org.bocra.backend.util.AttributeEncryptor.class)
    private String registrationNumber;

    @Convert(converter = bw.org.bocra.backend.util.AttributeEncryptor.class)
    private String contactEmail;

    @Convert(converter = bw.org.bocra.backend.util.AttributeEncryptor.class)
    private String contactPhone;

    @NotBlank(message = "License type is required")
    private String licenseType;

    @Column(columnDefinition = "TEXT")
    private String technicalProposal;

    @Column(columnDefinition = "TEXT")
    private String financialProvision;

    private String status = "PENDING";

    @ElementCollection
    @CollectionTable(name = "license_documents", joinColumns = @JoinColumn(name = "license_id"))
    @Column(name = "document_url")
    private List<String> documents = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String adminReviewNotes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;

    private LocalDateTime updatedAt;
}
