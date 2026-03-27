package bw.org.bocra.backend.config;

import bw.org.bocra.backend.model.Document;
import bw.org.bocra.backend.model.NewsArticle;
import bw.org.bocra.backend.model.Tender;
import bw.org.bocra.backend.model.Stat;
import bw.org.bocra.backend.repository.DocumentRepository;
import bw.org.bocra.backend.repository.NewsRepository;
import bw.org.bocra.backend.repository.TenderRepository;
import bw.org.bocra.backend.repository.StatRepository;
import bw.org.bocra.backend.repository.ComplaintRepository;
import bw.org.bocra.backend.model.Complaint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import bw.org.bocra.backend.model.Role;
import bw.org.bocra.backend.model.User;
import bw.org.bocra.backend.repository.UserRepository;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final NewsRepository newsRepository;
    private final TenderRepository tenderRepository;
    private final DocumentRepository documentRepository;
    private final StatRepository statRepository;
    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (newsRepository.count() == 0) {
            seedNews();
        }
        if (tenderRepository.count() == 0) {
            seedTenders();
        }
        if (documentRepository.count() == 0) {
            seedDocuments();
        }
        if (statRepository.count() == 0) {
            seedStats();
        }
        if (userRepository.count() == 0) {
            seedAdmin();
        }
        if (complaintRepository.count() == 0) {
            seedComplaints();
        }
    }

    private void seedNews() {
        NewsArticle n1 = new NewsArticle();
        n1.setTitle("BOCRA Launches New Cybersecurity Awareness Campaign");
        n1.setSummary("A national initiative to educate citizens on digital safety and threat prevention across Botswana.");
        n1.setContent("BOCRA has officially launched the 'SafeDigital BW' campaign today in Gaborone...");
        n1.setCategory("Press Release");
        n1.setImageUrl("https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80&w=2070");
        n1.setSlug("cybersecurity-campaign-2024");

        NewsArticle n2 = new NewsArticle();
        n2.setTitle("Type Approval Regulations Updated for 2024");
        n2.setSummary("New technical standards for telecommunications equipment have been gazetted to align with international ITU standards.");
        n2.setContent("The updated regulations aim to streamline the equipment import process...");
        n2.setCategory("Regulation");
        n2.setImageUrl("https://images.unsplash.com/photo-1518770660439-4636190af475?q=80&w=2070");
        n2.setSlug("type-approval-update-2024");

        newsRepository.saveAll(Arrays.asList(n1, n2));
        System.out.println(">> Seeded News Articles");
    }

    private void seedTenders() {
        Tender t1 = new Tender();
        t1.setTenderNumber("BOCRA/PT/001/2024");
        t1.setTitle("Provision of Comprehensive Cybersecurity Audit Services");
        t1.setType("Open Domestic Tender");
        t1.setPublishDate("2024-03-01");
        t1.setClosingDate("2024-06-15");
        t1.setStatus("OPEN");
        t1.setDescription("BOCRA invites reputable firms to conduct a full security audit of the national infrastructure...");

        Tender t2 = new Tender();
        t2.setTenderNumber("BOCRA/EOI/002/2024");
        t2.setTitle("Expression of Interest: Development of Cost Modelling Framework");
        t2.setType("Expression of Interest");
        t2.setPublishDate("2024-02-15");
        t2.setClosingDate("2024-05-30");
        t2.setStatus("OPEN");
        t2.setDescription("The objective of this project is to develop a robust retail cost modelling framework...");

        tenderRepository.saveAll(Arrays.asList(t1, t2));
        System.out.println(">> Seeded Tenders");
    }

    private void seedDocuments() {
        Document d1 = new Document();
        d1.setName("Communications Regulatory Authority Act, 2012");
        d1.setCategory("Acts and Legislation");
        d1.setSize("2.4 MB");
        d1.setDate("Apr 2013");
        d1.setUrl("https://www.bocra.org.bw/sites/default/files/documents/CRA%20Act%202012.pdf");

        Document d2 = new Document();
        d2.setName("BOCRA Licensing Framework");
        d2.setCategory("Guidelines & Frameworks");
        d2.setSize("3.2 MB");
        d2.setDate("Jan 2023");
        d2.setUrl("https://www.bocra.org.bw/sites/default/files/documents/Licensing%20Framework.pdf");

        Document d3 = new Document();
        d3.setName("BOCRA Annual Report 2022/2023");
        d3.setCategory("Annual Reports");
        d3.setSize("8.5 MB");
        d3.setDate("Oct 2023");
        d3.setUrl("https://www.bocra.org.bw/sites/default/files/documents/Annual%20Report%2022-23.pdf");

        documentRepository.saveAll(Arrays.asList(d1, d2, d3));
        System.out.println(">> Seeded Documents");
    }

    private void seedStats() {
        Stat s1 = new Stat();
        s1.setId("broadband");
        s1.setLabel("Broadband Subscriptions");
        s1.setValue(1.2);
        s1.setSuffix("M");
        s1.setDescription("Active mobile and fixed broadband subscriptions nationwide.");

        Stat s2 = new Stat();
        s2.setId("mobile");
        s2.setLabel("Mobile Penetration");
        s2.setValue(168.0);
        s2.setSuffix("%");
        s2.setDescription("Total mobile sim cards per 100 inhabitants.");

        Stat s3 = new Stat();
        s3.setId("complaints");
        s3.setLabel("Complaint Resolution");
        s3.setValue(94.5);
        s3.setSuffix("%");
        s3.setDescription("Percentage of consumer complaints resolved within SLA.");

        statRepository.saveAll(Arrays.asList(s1, s2, s3));
        System.out.println(">> Seeded Statistics");
    }

    private void seedAdmin() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
        System.out.println(">> Seeded Admin User: admin / admin123");
    }

    private void seedComplaints() {
        Complaint c1 = new Complaint();
        c1.setTicketId("BOCRA-X7Y2Z1A9");
        c1.setFullName("Thabo Motsamai");
        c1.setContactNumber("+267 71000001");
        c1.setEmail("thabo@example.bw");
        c1.setServiceProvider("Mascom Wireless");
        c1.setComplaintDetails("I am experiencing frequent dropouts in the Gaborone North area for the past 48 hours.");
        c1.setStatus("PENDING");

        Complaint c2 = new Complaint();
        c2.setTicketId("BOCRA-B3V8N4M2");
        c2.setFullName("Lesedi Kgosi");
        c2.setContactNumber("+267 72000002");
        c2.setEmail("lesedi@corporation.bw");
        c2.setServiceProvider("Orange Botswana");
        c2.setProviderReference("REF-998877");
        c2.setComplaintDetails("Incorrect billing on my enterprise fiber account for the month of February.");
        c2.setStatus("UNDER_REVIEW");

        complaintRepository.saveAll(Arrays.asList(c1, c2));
        System.out.println(">> Seeded Complaints");
    }
}
