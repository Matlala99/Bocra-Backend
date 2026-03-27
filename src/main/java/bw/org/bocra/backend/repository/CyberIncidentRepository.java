package bw.org.bocra.backend.repository;

import bw.org.bocra.backend.model.CyberIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CyberIncidentRepository extends JpaRepository<CyberIncident, Long> {
}
