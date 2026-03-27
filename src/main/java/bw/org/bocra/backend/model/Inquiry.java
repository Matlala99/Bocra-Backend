package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Inquiry type is required")
    private String inquiryType;

    @NotBlank(message = "Message cannot be empty")
    @Column(columnDefinition = "TEXT")
    private String message;

    private String status = "NEW";

    @Column(columnDefinition = "TEXT")
    private String responseMessage;

    private LocalDateTime respondedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime submittedAt;
}
