package com.laboratory.management.system.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PracticalRequestDto {
    private Long id;
    private String subject;
    private String grade;
    private String classSection;
    private LocalDate practicalDate;
    private String term;
    private String laboratorian;
    private Integer studentCount;
    private Integer expectedAttendance;
    private String experimentName;
    private String experimentType;
    private Boolean syllabusMandated;
    private String additionalNotes;
    private String status;
    private List<PracticalRequestItemDto> requiredItems = new ArrayList<>();
}
