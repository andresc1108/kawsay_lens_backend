package com.kawsay.controller;

import com.kawsay.DiagnosticReportManager;
import com.kawsay.factory.DiagnosticReportFactory;
import com.kawsay.model.DiagnosticReport;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnoses")
@CrossOrigin(origins = "http://localhost:3000")
public class DiagnosticController {

    private final DiagnosticReportManager manager = DiagnosticReportManager.getInstance();

    @PostMapping
    public DiagnosticReport create(@RequestBody Map<String, Object> body) {
        String detection      = (String) body.get("detection");
        double confidence     = ((Number) body.get("confidenceScore")).doubleValue();
        String severityStr    = (String) body.get("severity");
        String sessionId      = (String) body.getOrDefault("sessionId", "WEB");

        DiagnosticReportFactory.ReportSeverity severity =
            DiagnosticReportFactory.ReportSeverity.valueOf(severityStr);

        return manager.createAndRegisterReport(detection, confidence, severity, sessionId);
    }

    @GetMapping
    public List<DiagnosticReport> getAll() {
        return manager.getAllReports();
    }

    @GetMapping("/count")
    public Map<String, Integer> count() {
        return Map.of("total", manager.getTotalReportsProcessed());
    }

    @DeleteMapping
    public Map<String, String> clear() {
        manager.reset();
        return Map.of("status", "cleared");
    }
}
