package bw.org.bocra.backend.controller;

import bw.org.bocra.backend.model.LicenseApplication;
import bw.org.bocra.backend.service.LicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyForLicense(@Valid @RequestBody LicenseApplication application) {
        LicenseApplication saved = licenseService.submitApplication(application);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("applicationId", saved.getId());
        response.put("message", "License application submitted successfully. BOCRA will review your technical proposal shortly.");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LicenseApplication>> getAllApplications() {
        return ResponseEntity.ok(licenseService.getAllApplications());
    }

    @GetMapping("/track")
    public ResponseEntity<LicenseApplication> trackApplication(@RequestParam String regNum) {
        return ResponseEntity.ok(licenseService.trackApplication(regNum));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LicenseApplication> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        String notes = body.get("notes");
        return ResponseEntity.ok(licenseService.updateApplicationStatus(id, status, notes));
    }
}
