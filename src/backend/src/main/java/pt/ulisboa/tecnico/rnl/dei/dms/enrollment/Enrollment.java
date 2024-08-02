package pt.ulisboa.tecnico.rnl.dei.dms.enrollment;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
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

	public Enrollment(Fellowship fellowship, Candidate candidate) {
		this.fellowship = fellowship;
		this.candidate = candidate;
	}

	public Enrollment(Fellowship fellowship, CandidateDto candidateDto) {
		this.fellowship = fellowship;
		this.candidate = new Candidate(candidateDto);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
