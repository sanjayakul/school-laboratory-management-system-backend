package com.laboratory.management.system.service.impl;

import com.laboratory.management.system.exceptions.DataNotFoundException;
import com.laboratory.management.system.model.PracticalRequest;
import com.laboratory.management.system.model.PracticalRequestItem;
import com.laboratory.management.system.model.User;
import com.laboratory.management.system.model.dto.PracticalRequestDto;
import com.laboratory.management.system.model.dto.PracticalRequestItemDto;
import com.laboratory.management.system.repository.PracticalRequestRepository;
import com.laboratory.management.system.repository.UserRepository;
import com.laboratory.management.system.service.PracticalRequestService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.laboratory.management.system.util.Enums.ACTIVE;

@Service
public class PracticalRequestServiceImpl implements PracticalRequestService {

    private static final Logger logger = LoggerFactory.getLogger(PracticalRequestServiceImpl.class);

    private final PracticalRequestRepository practicalRequestRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public PracticalRequestServiceImpl(PracticalRequestRepository practicalRequestRepository,
                                       UserRepository userRepository,
                                       JavaMailSender mailSender) {
        this.practicalRequestRepository = practicalRequestRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public PracticalRequestDto createPracticalRequest(PracticalRequestDto dto) {
        validateMandatoryFields(dto);
        validateRequiredItems(dto);
        PracticalRequest entity = mapToEntity(dto);
        if (entity.getExpectedAttendance() == null) {
            entity.setExpectedAttendance(100);
        }
        if (entity.getStatus() == null || entity.getStatus().trim().isEmpty()) {
            entity.setStatus(ACTIVE.value());
        }
        PracticalRequest saved = practicalRequestRepository.save(entity);
        sendPracticalRequestEmail(saved);
        logger.info("Created practical request id: {}", saved.getId());
        return mapToDto(saved);
    }

    @Override
    public List<PracticalRequestDto> getAllPracticalRequests() {
        return practicalRequestRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PracticalRequestDto getPracticalRequestById(Long id) {
        PracticalRequest entity = practicalRequestRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Practical request not found for id: " + id));
        return mapToDto(entity);
    }

    @Override
    public PracticalRequestDto updatePracticalRequest(Long id, PracticalRequestDto dto) {
        validateMandatoryFields(dto);
        validateRequiredItems(dto);
        PracticalRequest existing = practicalRequestRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Practical request not found for id: " + id));

        existing.setSubject(dto.getSubject());
        existing.setGrade(dto.getGrade());
        existing.setClassSection(dto.getClassSection());
        existing.setPracticalDate(dto.getPracticalDate());
        existing.setTerm(dto.getTerm());
        existing.setLaboratorian(dto.getLaboratorian());
        existing.setStudentCount(dto.getStudentCount());
        existing.setExpectedAttendance(dto.getExpectedAttendance() != null ? dto.getExpectedAttendance() : 100);
        existing.setExperimentName(dto.getExperimentName());
        existing.setExperimentType(dto.getExperimentType());
        existing.setSyllabusMandated(dto.getSyllabusMandated());
        existing.setAdditionalNotes(dto.getAdditionalNotes());
        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            existing.setStatus(dto.getStatus());
        }
        existing.setRequiredItems(mapItems(dto.getRequiredItems()));

        PracticalRequest saved = practicalRequestRepository.save(existing);
        logger.info("Updated practical request id: {}", saved.getId());
        return mapToDto(saved);
    }

    @Override
    public void deletePracticalRequest(Long id) {
        if (!practicalRequestRepository.existsById(id)) {
            throw new DataNotFoundException("Practical request not found for id: " + id);
        }
        practicalRequestRepository.deleteById(id);
        logger.info("Deleted practical request id: {}", id);
    }

    private PracticalRequest mapToEntity(PracticalRequestDto dto) {
        PracticalRequest entity = new PracticalRequest();
        entity.setId(dto.getId());
        entity.setSubject(dto.getSubject());
        entity.setGrade(dto.getGrade());
        entity.setClassSection(dto.getClassSection());
        entity.setPracticalDate(dto.getPracticalDate());
        entity.setTerm(dto.getTerm());
        entity.setLaboratorian(dto.getLaboratorian());
        entity.setStudentCount(dto.getStudentCount());
        entity.setExpectedAttendance(dto.getExpectedAttendance());
        entity.setExperimentName(dto.getExperimentName());
        entity.setExperimentType(dto.getExperimentType());
        entity.setSyllabusMandated(dto.getSyllabusMandated() != null ? dto.getSyllabusMandated() : Boolean.FALSE);
        entity.setAdditionalNotes(dto.getAdditionalNotes());
        entity.setStatus(dto.getStatus());
        entity.setRequiredItems(mapItems(dto.getRequiredItems()));
        return entity;
    }

    private PracticalRequestDto mapToDto(PracticalRequest entity) {
        PracticalRequestDto dto = new PracticalRequestDto();
        dto.setId(entity.getId());
        dto.setSubject(entity.getSubject());
        dto.setGrade(entity.getGrade());
        dto.setClassSection(entity.getClassSection());
        dto.setPracticalDate(entity.getPracticalDate());
        dto.setTerm(entity.getTerm());
        dto.setLaboratorian(entity.getLaboratorian());
        dto.setStudentCount(entity.getStudentCount());
        dto.setExpectedAttendance(entity.getExpectedAttendance());
        dto.setExperimentName(entity.getExperimentName());
        dto.setExperimentType(entity.getExperimentType());
        dto.setSyllabusMandated(entity.getSyllabusMandated());
        dto.setAdditionalNotes(entity.getAdditionalNotes());
        dto.setStatus(entity.getStatus());

        List<PracticalRequestItemDto> items = new ArrayList<>();
        for (PracticalRequestItem item : entity.getRequiredItems()) {
            PracticalRequestItemDto itemDto = new PracticalRequestItemDto();
            itemDto.setId(item.getId());
            itemDto.setItemType(item.getItemType());
            itemDto.setItemName(item.getItemName());
            itemDto.setRequiredVolume(item.getRequiredVolume());
            itemDto.setUnit(item.getUnit());
            items.add(itemDto);
        }
        dto.setRequiredItems(items);
        return dto;
    }

    private List<PracticalRequestItem> mapItems(List<PracticalRequestItemDto> itemDtos) {
        List<PracticalRequestItem> items = new ArrayList<>();
        if (itemDtos == null) {
            return items;
        }
        for (PracticalRequestItemDto itemDto : itemDtos) {
            PracticalRequestItem item = new PracticalRequestItem();
            item.setId(itemDto.getId());
            item.setItemType(itemDto.getItemType());
            item.setItemName(itemDto.getItemName());
            item.setRequiredVolume(itemDto.getRequiredVolume());
            item.setUnit(itemDto.getUnit());
            items.add(item);
        }
        return items;
    }

    private void validateRequiredItems(PracticalRequestDto dto) {
        if (dto.getRequiredItems() == null || dto.getRequiredItems().isEmpty()) {
            throw new IllegalArgumentException("Required items section is mandatory");
        }

        for (PracticalRequestItemDto item : dto.getRequiredItems()) {
            if (isBlank(item.getItemType())) {
                throw new IllegalArgumentException("Required item type is mandatory");
            }
            if (isBlank(item.getItemName())) {
                throw new IllegalArgumentException("Required item name is mandatory");
            }
        }
    }

    private void validateMandatoryFields(PracticalRequestDto dto) {
        if (isBlank(dto.getSubject())) {
            throw new IllegalArgumentException("Subject is mandatory");
        }
        if (isBlank(dto.getGrade())) {
            throw new IllegalArgumentException("Grade is mandatory");
        }
        if (dto.getPracticalDate() == null) {
            throw new IllegalArgumentException("Practical date is mandatory");
        }
        if (isBlank(dto.getTerm())) {
            throw new IllegalArgumentException("Term is mandatory");
        }
        if (isBlank(dto.getLaboratorian())) {
            throw new IllegalArgumentException("Laboratorian is mandatory");
        }
        if (dto.getStudentCount() == null) {
            throw new IllegalArgumentException("Number of students is mandatory");
        }
        if (isBlank(dto.getExperimentName())) {
            throw new IllegalArgumentException("Experiment name is mandatory");
        }
        if (isBlank(dto.getExperimentType())) {
            throw new IllegalArgumentException("Experiment type is mandatory");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void sendPracticalRequestEmail(PracticalRequest request) {
        try {
            String recipient = resolveLaboratorianEmail(request.getLaboratorian());
            if (isBlank(recipient)) {
                logger.warn("Skipping practical request notification. Email not found for laboratorian: {}", request.getLaboratorian());
                return;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(recipient);
            helper.setSubject("New Practical Request Assigned");

            String html = loadPracticalRequestTemplate();
            html = html.replace("{{LABORATORIAN}}", safeValue(request.getLaboratorian()));
            html = html.replace("{{SUBJECT}}", safeValue(request.getSubject()));
            html = html.replace("{{GRADE}}", safeValue(request.getGrade()));
            html = html.replace("{{CLASS_SECTION}}", safeValue(request.getClassSection()));
            html = html.replace("{{PRACTICAL_DATE}}", request.getPracticalDate() == null ? "" : request.getPracticalDate().toString());
            html = html.replace("{{TERM}}", safeValue(request.getTerm()));
            html = html.replace("{{EXPERIMENT_NAME}}", safeValue(request.getExperimentName()));
            html = html.replace("{{EXPERIMENT_TYPE}}", safeValue(request.getExperimentType()));
            html = html.replace("{{STUDENT_COUNT}}", request.getStudentCount() == null ? "" : String.valueOf(request.getStudentCount()));
            html = html.replace("{{EXPECTED_ATTENDANCE}}", request.getExpectedAttendance() == null ? "" : String.valueOf(request.getExpectedAttendance()));
            html = html.replace("{{ADDITIONAL_NOTES}}", safeValue(request.getAdditionalNotes()));

            helper.setText(html, true);

            ClassPathResource logoResource = new ClassPathResource("assets/logo.png");
            helper.addInline("logo", logoResource);

            mailSender.send(mimeMessage);
            logger.info("Practical request notification sent to: {}", recipient);
        } catch (Exception ex) {
            logger.error("Failed to send practical request email for request id: {}", request.getId(), ex);
        }
    }

    private String resolveLaboratorianEmail(String laboratorian) {
        if (isBlank(laboratorian)) {
            return null;
        }

        String value = laboratorian.trim();
        if (value.contains("@")) {
            return value;
        }

        return userRepository.findByUsernameIgnoreCase(value)
                .map(User::getEmail)
                .orElseGet(() -> userRepository.findByFullNameIgnoreCase(value)
                        .map(User::getEmail)
                        .orElse(null));
    }

    private String loadPracticalRequestTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/practical-request-created-email.html");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }
}

