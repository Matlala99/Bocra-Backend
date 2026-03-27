package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cyber_incidents")
public class CyberIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Incident ID must be generated")
    @Column(unique = true, nullable = false)
    private String incidentId;

    @NotBlank(message = "Reporter Type is required")
    private String reporterType;

    private String organizationName;

    @NotBlank(message = "Incident Type is required")
    private String incidentType;

    @NotBlank(message = "Date of incident is required")
    private String dateOfIncident;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Description of the incident is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "INVESTIGATING";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime reportedAt;
}
