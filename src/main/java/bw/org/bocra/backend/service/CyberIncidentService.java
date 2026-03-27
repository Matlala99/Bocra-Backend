package bw.org.bocra.backend.service;

import bw.org.bocra.backend.model.CyberIncident;
import bw.org.bocra.backend.repository.CyberIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CyberIncidentService {

    private final CyberIncidentRepository cyberIncidentRepository;

    @Transactional
    public CyberIncident reportIncident(CyberIncident incident) {
        String uniqueHash = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        incident.setIncidentId("INC-" + uniqueHash);
        return cyberIncidentRepository.save(incident);
    }
}
