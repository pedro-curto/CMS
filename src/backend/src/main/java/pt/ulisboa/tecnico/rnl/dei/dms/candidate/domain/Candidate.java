package pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String istId;
	private String name;
	private String email;
	@OneToMany(mappedBy = "candidate")
	private List<Enrollment> enrollments = new ArrayList<>();

	public Candidate() {}

	public Candidate(String istId, String name, String email) {
		this.istId = istId;
		this.name = name;
		this.email = email;
	}

	public Candidate(CandidateDto candidateDto) {
		this.istId = candidateDto.getIstId();
		this.name = candidateDto.getName();
		this.email = candidateDto.getEmail();
	}

	public Long getId() {
		return id;
	}

	public String getIstId() { return istId; }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<Enrollment> getEnrollments() { return enrollments; }

	public void setId(Long id) { this.id = id; }

	public void setIstId(String istId) {
		this.istId = istId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

	public void addEnrollment(Enrollment enrollment) {
		this.enrollments.add(enrollment);
	}

	public void removeEnrollment(Enrollment enrollment) {
		this.enrollments.remove(enrollment);
	}

}
