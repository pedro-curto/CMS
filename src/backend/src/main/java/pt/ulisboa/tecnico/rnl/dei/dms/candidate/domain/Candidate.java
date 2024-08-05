package pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String istId;
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enrollment> enrollments = new ArrayList<>();

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

	public void addEnrollment(Enrollment enrollment) { this.enrollments.add(enrollment); }
	public void removeEnrollment(Enrollment enrollment) {
		this.enrollments.remove(enrollment);
	}

	public void update(CandidateDto candidateDto) {
		setName(candidateDto.getName());
		setEmail(candidateDto.getEmail());
		setIstId(candidateDto.getIstId());
	}
}
