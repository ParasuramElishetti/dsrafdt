package com.example.reportVehicleModel1.repository;

import com.example.reportVehicleModel1.model.ReportVehicleModel;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;

public interface ReportRepository {


    @Aggregation(pipeline = {"{\n" +
            "    $match: {\n" +
            "      vehicle_registration_number: \"KA03KM4070\"\n" +
            "    }\n" +
            "  },\n",
            "  {\n" +
                    "    $lookup: {\n" +
                    "      from: \"asset_gps_device_mapping\",\n" +
                    "      localField: \"asset_gps_device_mapping\",\n" +
                    "      foreignField: \"_id\",\n" +
                    "      as: \"gpsMapping\"\n" +
                    "    }\n" +
                    "  },\n",
            "  {\n" +
                    "    $unwind: \"$gpsMapping\"\n" +
                    "  },\n",
            "  {\n" +
                    "    $lookup: {\n" +
                    "      from: \"configurations\",\n" +
                    "      localField: \"vehicle_status\",\n" +
                    "      foreignField: \"_id\",\n" +
                    "      as: \"statusMapping\"\n" +
                    "    }\n" +
                    "  },\n",
            "  {\n" +
                    "    $unwind: \"$statusMapping\"\n" +
                    "  },\n",
            "  {\n" +
                    "    $group: {\n" +
                    "      _id: {\n" +
                    "        vehicle_registration_number:\n" +
                    "          \"$vehicle_registration_number\",\n" +
                    "        status: \"$statusMapping.value\"\n" +
                    "      },\n" +
                    "      count: {\n" +
                    "        $sum: 1\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n",
            "  {\n" +
                    "    $group: {\n" +
                    "      _id: \"$_id.vehicle_registration_number\",\n" +
                    "      statusCounts: {\n" +
                    "        $push: {\n" +
                    "          status: \"$_id.status\",\n" +
                    "          count: \"$count\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      totalCount: {\n" +
                    "        $sum: \"$count\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n",
            "  {\n" +
                    "    $addFields: {\n" +
                    "      statusDistribution: {\n" +
                    "        $map: {\n" +
                    "          input: [\n" +
                    "            \"Offline\",\n" +
                    "            \"Sleep\",\n" +
                    "            \"Moving\",\n" +
                    "            \"Halted\"\n" +
                    "          ],\n" +
                    "          as: \"status\",\n" +
                    "          in: {\n" +
                    "            status: \"$$status\",\n" +
                    "            count: {\n" +
                    "              $let: {\n" +
                    "                vars: {\n" +
                    "                  statusCount: {\n" +
                    "                    $arrayElemAt: [\n" +
                    "                      {\n" +
                    "                        $filter: {\n" +
                    "                          input: \"$statusCounts\",\n" +
                    "                          cond: {\n" +
                    "                            $eq: [\n" +
                    "                              \"$$this.status\",\n" +
                    "                              \"$$status\"\n" +
                    "                            ]\n" +
                    "                          }\n" +
                    "                        }\n" +
                    "                      },\n" +
                    "                      0\n" +
                    "                    ]\n" +
                    "                  }\n" +
                    "                },\n" +
                    "                in: {\n" +
                    "                  $ifNull: [\n" +
                    "                    \"$$statusCount.count\",\n" +
                    "                    0\n" +
                    "                  ]\n" +
                    "                }\n" +
                    "              }\n" +
                    "            },\n" +
                    "            percentage: {\n" +
                    "              $multiply: [\n" +
                    "                {\n" +
                    "                  $divide: [\n" +
                    "                    {\n" +
                    "                      $let: {\n" +
                    "                        vars: {\n" +
                    "                          statusCount: {\n" +
                    "                            $arrayElemAt: [\n" +
                    "                              {\n" +
                    "                                $filter: {\n" +
                    "                                  input:\n" +
                    "                                    \"$statusCounts\",\n" +
                    "                                  cond: {\n" +
                    "                                    $eq: [\n" +
                    "                                      \"$$this.status\",\n" +
                    "                                      \"$$status\"\n" +
                    "                                    ]\n" +
                    "                                  }\n" +
                    "                                }\n" +
                    "                              },\n" +
                    "                              0\n" +
                    "                            ]\n" +
                    "                          }\n" +
                    "                        },\n" +
                    "                        in: {\n" +
                    "                          $ifNull: [\n" +
                    "                            \"$$statusCount.count\",\n" +
                    "                            0\n" +
                    "                          ]\n" +
                    "                        }\n" +
                    "                      }\n" +
                    "                    },\n" +
                    "                    \"$totalCount\"\n" +
                    "                  ]\n" +
                    "                },\n" +
                    "                100\n" +
                    "              ]\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }\n" +
                    "  },\n",
            "  {\n" +
                    "    $project: {\n" +
                    "      _id: 0,\n" +
                    "      vehicle_registration_number: \"$_id\",\n" +
                    "      data: \"$statusDistribution\"\n" +
                    "    }\n" +
                    "  }"})

    List<ReportVehicleModel> getReports(ReportVehicleModel report);
}
