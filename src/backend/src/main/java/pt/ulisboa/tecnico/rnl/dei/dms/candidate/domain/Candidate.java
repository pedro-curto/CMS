package pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain;

import jakarta.persistence.*;
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
	private String istId;
	private String name;
	private String email;
	@OneToMany(mappedBy = "candidate")
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

}
