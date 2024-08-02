package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

@Entity
@Table(name = "enrollment")
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String motivation;
	private String enrollmentDateTime;
	@ManyToOne
	private Fellowship fellowship;
	@ManyToOne
	private Candidate candidate;

	public Enrollment() {
	}

	public Enrollment(Fellowship fellowship, Candidate candidate, EnrollmentDto enrollmentDto) {
		this.fellowship = fellowship;
		this.candidate = candidate;
		this.motivation = enrollmentDto.getMotivation();
		this.enrollmentDateTime = enrollmentDto.getEnrollmentDateTime();
	}

	public Enrollment(String motivation, String enrollmentDateTime, Fellowship fellowship, Candidate candidate) {
		this.motivation = motivation;
		this.enrollmentDateTime = enrollmentDateTime;
		this.fellowship = fellowship;
		this.candidate = candidate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) { this.motivation = motivation; }

	public String getEnrollmentDateTime() { return enrollmentDateTime; }

	public void setEnrollmentDateTime(String enrollmentDateTime) { this.enrollmentDateTime = enrollmentDateTime; }

	public Fellowship getFellowship() {
		return fellowship;
	}

	public void setFellowship(Fellowship fellowship) {
		this.fellowship = fellowship;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

}
