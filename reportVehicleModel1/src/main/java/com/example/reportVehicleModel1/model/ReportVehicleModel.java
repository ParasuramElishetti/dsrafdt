package com.example.reportVehicleModel1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("asset_iot_status")
@Getter
@Setter
public class ReportVehicleModel {

    private String vehicle_registration_number;
    private List<StatusDistribution> data;
}
