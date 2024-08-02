package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public Enrollment(Fellowship fellowship, Candidate candidate, EnrollmentDto enrollmentDto) {
		setFellowship(fellowship);
		setCandidate(candidate);
		this.motivation = enrollmentDto.getMotivation();
		this.enrollmentDateTime = enrollmentDto.getEnrollmentDateTime();
	}

	public Enrollment(String motivation, String enrollmentDateTime, Fellowship fellowship, Candidate candidate) {
		this.motivation = motivation;
		this.enrollmentDateTime = enrollmentDateTime;
		this.fellowship = fellowship;
		this.candidate = candidate;
	}

	public void setFellowship(Fellowship fellowship) {
		this.fellowship = fellowship;
		this.fellowship.addEnrollment(this);
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
		this.candidate.addEnrollment(this);
	}

}
