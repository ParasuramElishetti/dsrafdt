package com.example.reportVehicleModel1.controller;
import com.example.reportVehicleModel1.model.ReportVehicleModel;
import com.example.reportVehicleModel1.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/getreports")
    public List<ReportVehicleModel> getReport(ReportVehicleModel report) {
        return service.getReports(report);
    }
}

