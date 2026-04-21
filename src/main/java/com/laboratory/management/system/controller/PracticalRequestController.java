package com.laboratory.management.system.controller;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.dto.ApiResponseDto;
import com.laboratory.management.system.model.dto.PracticalRequestDto;
import com.laboratory.management.system.service.PracticalRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "PracticalRequestController", description = "The Practical Request API Services")
public class PracticalRequestController {

	private static final Logger logger = LoggerFactory.getLogger(PracticalRequestController.class);

	private final PracticalRequestService practicalRequestService;

	public PracticalRequestController(PracticalRequestService practicalRequestService) {
		this.practicalRequestService = practicalRequestService;
	}

	@PostMapping(value = "/practical-requests", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create Practical Request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful Operation",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
	public ResponseEntity<ApiResponseDto> createPracticalRequest(@RequestBody PracticalRequestDto dto) {
		try {
			return ResponseEntity.ok(success(practicalRequestService.createPracticalRequest(dto), "practical request created successfully"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(failure(e.getMessage()));
		} catch (Exception e) {
			logger.error("PracticalRequestController -> createPracticalRequest() failed", e);
			return ResponseEntity.internalServerError().body(failure(e.getMessage()));
		}
	}

	@GetMapping(value = "/practical-requests", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all Practical Requests")
	public ResponseEntity<ApiResponseDto> getAllPracticalRequests() {
		List<PracticalRequestDto> data = practicalRequestService.getAllPracticalRequests();
		return ResponseEntity.ok(success(data, "practical requests fetched successfully"));
	}

	@GetMapping(value = "/practical-requests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Practical Request by Id")
	public ResponseEntity<ApiResponseDto> getPracticalRequestById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(success(practicalRequestService.getPracticalRequestById(id), "practical request fetched successfully"));
		} catch (DataNotFoundException e) {
			return ResponseEntity.status(404).body(failure(e.getMessage()));
		}
	}

	@PutMapping(value = "/practical-requests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update Practical Request")
	public ResponseEntity<ApiResponseDto> updatePracticalRequest(@PathVariable Long id, @RequestBody PracticalRequestDto dto) {
		try {
			return ResponseEntity.ok(success(practicalRequestService.updatePracticalRequest(id, dto), "practical request updated successfully"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(failure(e.getMessage()));
		} catch (DataNotFoundException e) {
			return ResponseEntity.status(404).body(failure(e.getMessage()));
		}
	}

	@DeleteMapping(value = "/practical-requests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Delete Practical Request")
	public ResponseEntity<ApiResponseDto> deletePracticalRequest(@PathVariable Long id) {
		try {
			practicalRequestService.deletePracticalRequest(id);
			return ResponseEntity.ok(success(null, "practical request deleted successfully"));
		} catch (DataNotFoundException e) {
			return ResponseEntity.status(404).body(failure(e.getMessage()));
		}
	}

	private ApiResponseDto success(Object data, String message) {
		ApiResponseDto response = new ApiResponseDto();
		response.setData(data);
		response.setMessage(message);
		return response;
	}

	private ApiResponseDto failure(String message) {
		ApiResponseDto response = new ApiResponseDto();
		response.setData(null);
		response.setMessage(message);
		return response;
	}
}
