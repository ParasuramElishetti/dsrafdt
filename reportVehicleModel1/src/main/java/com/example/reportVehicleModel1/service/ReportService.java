package com.example.reportVehicleModel1.service;


import com.example.reportVehicleModel1.model.ReportVehicleModel;
import com.example.reportVehicleModel1.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repository;

    public List<ReportVehicleModel> getReports(ReportVehicleModel report) {
        List<ReportVehicleModel> data = repository.getReports(report);
        return data;
    }
}
