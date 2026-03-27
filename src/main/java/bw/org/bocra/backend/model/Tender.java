package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tenders")
public class Tender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tender Number is required")
    @Column(unique = true)
    private String tenderNumber;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Type is required")
    private String type;

    private String publishDate;

    private String closingDate;

    private String status = "OPEN";

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
