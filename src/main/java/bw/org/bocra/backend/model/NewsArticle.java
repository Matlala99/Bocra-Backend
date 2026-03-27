package bw.org.bocra.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news")
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Summary is required")
    @Column(columnDefinition = "TEXT")
    private String summary;

    @NotBlank(message = "Content is required")
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @NotBlank(message = "Category is required")
    private String category;

    private String imageUrl;

    private String slug;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime publishedAt;
}
