package com.laboratory.management.system.service;

import com.laboratory.management.system.model.dto.PracticalRequestDto;

import java.util.List;

public interface PracticalRequestService {

    PracticalRequestDto createPracticalRequest(PracticalRequestDto dto);

    List<PracticalRequestDto> getAllPracticalRequests();

    PracticalRequestDto getPracticalRequestById(Long id);

    PracticalRequestDto updatePracticalRequest(Long id, PracticalRequestDto dto);

    void deletePracticalRequest(Long id);
}
