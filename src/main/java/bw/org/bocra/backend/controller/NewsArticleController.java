package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.NewsArticle;
import bw.org.bocra.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsArticle>> getAllNews(@RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(newsService.getNewsByCategory(category));
        }
        return ResponseEntity.ok(newsService.getAllNews());
    }

    // Secured: Only admins should be able to post news
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NewsArticle> createNews(@RequestBody NewsArticle article) {
        return ResponseEntity.ok(newsService.createNews(article));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateNews(@PathVariable Long id, @RequestBody NewsArticle article) {
        return ResponseEntity.ok(newsService.updateNews(id, article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
