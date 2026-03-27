package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticketId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Service provider is required")
    private String serviceProvider;

    private String providerReference;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Complaint details are required")
    @Column(columnDefinition = "TEXT")
    private String complaintDetails;

    private String status = "PENDING";

    @Column(columnDefinition = "TEXT")
    private String adminResponse;

    private LocalDateTime resolutionDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
