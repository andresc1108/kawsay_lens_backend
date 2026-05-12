package com.kawsay;

import com.kawsay.factory.DiagnosticReportFactory;
import com.kawsay.model.DiagnosticReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern - DiagnosticReportManager
 * Gestiona una instancia única del administrador de reportes diagnósticos
 */
public class DiagnosticReportManager {
    private static DiagnosticReportManager instance;
    private static final Object lock = new Object();

    private final List<DiagnosticReport> reports = new ArrayList<>();

    private DiagnosticReportManager() {}

    public static DiagnosticReportManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DiagnosticReportManager();
                }
            }
        }
        return instance;
    }

    public DiagnosticReport createAndRegisterReport(
        String detection,
        double confidenceScore,
        DiagnosticReportFactory.ReportSeverity severity,
        String sessionId
    ) {
        DiagnosticReport report = DiagnosticReportFactory.createReport(
            detection, confidenceScore, severity, sessionId
        );
        reports.add(report);
        System.out.println("Reporte #" + reports.size() + " registrado: " + report.getReportId());
        return report;
    }

    public List<DiagnosticReport> getAllReports() {
        return new ArrayList<>(reports);
    }

    public int getTotalReportsProcessed() {
        return reports.size();
    }

    public void reset() {
        reports.clear();
    }
}
