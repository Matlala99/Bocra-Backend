package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.NewsArticle;
import bw.org.bocra.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public List<NewsArticle> getAllNews() {
        return newsRepository.findAllByOrderByPublishedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<NewsArticle> getNewsByCategory(String category) {
        return newsRepository.findByCategory(category);
    }

    @Transactional
    public NewsArticle createNews(NewsArticle article) {
        // Generate a URL friendly slug from the title
        article.setSlug(article.getTitle().toLowerCase().replaceAll("[^a-z0-9]", "-"));
        return newsRepository.save(article);
    }

    @Transactional
    public NewsArticle updateNews(Long id, NewsArticle articleDetails) {
        NewsArticle article = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News article not found"));
        
        article.setTitle(articleDetails.getTitle());
        article.setSummary(articleDetails.getSummary());
        article.setContent(articleDetails.getContent());
        article.setCategory(articleDetails.getCategory());
        article.setImageUrl(articleDetails.getImageUrl());
        article.setSlug(articleDetails.getTitle().toLowerCase().replaceAll("[^a-z0-9]", "-"));
        
        return newsRepository.save(article);
    }

    @Transactional
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
