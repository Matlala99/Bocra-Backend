package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.NewsArticle;
import bw.org.bocra.backend.model.Tender;
import bw.org.bocra.backend.model.Document;
import bw.org.bocra.backend.repository.NewsRepository;
import bw.org.bocra.backend.repository.TenderRepository;
import bw.org.bocra.backend.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final NewsRepository newsRepository;
    private final TenderRepository tenderRepository;
    private final DocumentRepository documentRepository;

    public List<Map<String, Object>> search(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        String q = query.toLowerCase();

        // Search News
        newsRepository.findAll().stream()
                .filter(n -> n.getTitle().toLowerCase().contains(q) || n.getSummary().toLowerCase().contains(q))
                .forEach(n -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "News");
                    map.put("title", n.getTitle());
                    map.put("url", "/news/" + n.getSlug());
                    map.put("description", n.getSummary());
                    results.add(map);
                });

        // Search Tenders
        tenderRepository.findAll().stream()
                .filter(t -> t.getTitle().toLowerCase().contains(q) || t.getTenderNumber().toLowerCase().contains(q))
                .forEach(t -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "Tender");
                    map.put("title", t.getTitle());
                    map.put("url", "/tenders");
                    map.put("description", t.getTenderNumber() + " - " + t.getStatus());
                    results.add(map);
                });

        return results;
    }
}
