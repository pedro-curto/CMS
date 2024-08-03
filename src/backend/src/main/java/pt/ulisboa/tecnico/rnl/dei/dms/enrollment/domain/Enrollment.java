package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

@Entity
// guarantee that the combination of fellowship_id and candidate_id is unique
@Table(name = "enrollment",
	uniqueConstraints = @UniqueConstraint(columnNames = {"fellowship_id", "candidate_id"}))
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
	@JoinColumn(name = "fellowship_id")
	@NotNull
	private Fellowship fellowship;
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	@NotNull
	private Candidate candidate;
	// when an enrollment is removed, the related evaluation is also removed
	@OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
	private Evaluation evaluation;

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

	public void addEvaluation(Evaluation evaluation) { this.evaluation = evaluation; }
}
