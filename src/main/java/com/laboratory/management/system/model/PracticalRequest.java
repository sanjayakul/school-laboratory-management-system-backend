package com.laboratory.management.system.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "practical_request")
@Data
public class PracticalRequest extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "subject", nullable = false, length = 100)
	private String subject;

	@Column(name = "grade", nullable = false, length = 50)
	private String grade;

	@Column(name = "class_section", length = 100)
	private String classSection;

	@Column(name = "practical_date", nullable = false)
	private LocalDate practicalDate;

	@Column(name = "term", nullable = false, length = 50)
	private String term;

	@Column(name = "laboratorian", nullable = false, length = 100)
	private String laboratorian;

	@Column(name = "student_count", nullable = false)
	private Integer studentCount;

	@Column(name = "expected_attendance")
	private Integer expectedAttendance;

	@Column(name = "experiment_name", nullable = false, length = 255)
	private String experimentName;

	@Column(name = "experiment_type", nullable = false, length = 100)
	private String experimentType;

	@Column(name = "syllabus_mandated", nullable = false)
	private Boolean syllabusMandated;

	@Column(name = "additional_notes", length = 1000)
	private String additionalNotes;

	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@OneToMany(mappedBy = "practicalRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PracticalRequestItem> requiredItems = new ArrayList<>();

	public void setRequiredItems(List<PracticalRequestItem> requiredItems) {
		this.requiredItems.clear();
		if (requiredItems == null) {
			return;
		}
		for (PracticalRequestItem item : requiredItems) {
			item.setPracticalRequest(this);
			this.requiredItems.add(item);
		}
	}
}

