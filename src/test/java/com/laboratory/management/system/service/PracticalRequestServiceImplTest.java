package com.laboratory.management.system.service;

import com.laboratory.management.system.model.PracticalRequest;
import com.laboratory.management.system.model.dto.PracticalRequestDto;
import com.laboratory.management.system.model.dto.PracticalRequestItemDto;
import com.laboratory.management.system.repository.PracticalRequestRepository;
import com.laboratory.management.system.repository.UserRepository;
import com.laboratory.management.system.service.impl.PracticalRequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PracticalRequestServiceImplTest {

    @Mock
    private PracticalRequestRepository practicalRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private PracticalRequestServiceImpl practicalRequestService;

    @Test
    void createPracticalRequest_shouldDefaultExpectedAttendanceTo100() {
        PracticalRequestDto request = validRequest();
        request.setExpectedAttendance(null);

        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((javax.mail.Session) null));

        when(practicalRequestRepository.save(any(PracticalRequest.class))).thenAnswer(invocation -> {
            PracticalRequest entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        PracticalRequestDto response = practicalRequestService.createPracticalRequest(request);

        Assertions.assertEquals(100, response.getExpectedAttendance());
        Assertions.assertEquals(1L, response.getId());
        verify(mailSender, times(1)).createMimeMessage();
    }

    @Test
    void createPracticalRequest_shouldFailWhenRequiredItemsMissing() {
        PracticalRequestDto request = validRequest();
        request.setRequiredItems(Collections.emptyList());

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> practicalRequestService.createPracticalRequest(request));

        Assertions.assertEquals("Required items section is mandatory", ex.getMessage());
    }

    private PracticalRequestDto validRequest() {
        PracticalRequestDto dto = new PracticalRequestDto();
        dto.setSubject("Chemistry");
        dto.setGrade("Grade 9");
        dto.setClassSection("9A");
        dto.setPracticalDate(LocalDate.now().plusDays(1));
        dto.setTerm("Term 1");
        dto.setLaboratorian("Lab Assistant 1");
        dto.setStudentCount(35);
        dto.setExperimentName("Acid-Base Titration");
        dto.setExperimentType("Qualitative");
        dto.setSyllabusMandated(Boolean.TRUE);
        dto.setAdditionalNotes("Handle acids carefully");

        PracticalRequestItemDto item = new PracticalRequestItemDto();
        item.setItemType("Chemical");
        item.setItemName("Hydrochloric Acid");
        item.setUnit("ml");

        dto.setRequiredItems(Collections.singletonList(item));
        return dto;
    }
}

