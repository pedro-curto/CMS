package pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate")
@Data
@NoArgsConstructor
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long istId;
	private String name;
	private String email;
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enrollment> enrollments = new ArrayList<>();

	public Candidate(Long istId, String name, String email) {
		this.istId = istId;
		this.name = name;
		this.email = email;
		verifyInvariants();
	}

	public Candidate(CandidateDto candidateDto) {
		this.istId = candidateDto.getIstId();
		this.name = candidateDto.getName();
		this.email = candidateDto.getEmail();
		verifyInvariants();
	}

	public void addEnrollment(Enrollment enrollment) { this.enrollments.add(enrollment); }
	public void removeEnrollment(Enrollment enrollment) {
		this.enrollments.remove(enrollment);
	}

	public void update(CandidateDto candidateDto) {
		setName(candidateDto.getName());
		setEmail(candidateDto.getEmail());
		setIstId(candidateDto.getIstId());
		verifyInvariants();
	}

	private void verifyInvariants() {
		istIDIsRequired();
		nameIsRequired();
		emailIsRequired();
	}

	private void istIDIsRequired() {
		if (istId == null) {
			throw new CMSException(IST_ID_REQUIRED);
		}
	}

	private void nameIsRequired() {
		if (name == null || name.isBlank()) {
			throw new CMSException(NAME_REQUIRED, name);
		}
	}

	private void emailIsRequired() {
		if (email == null || email.isBlank()) {
			throw new CMSException(EMAIL_REQUIRED, email);
		}
		// regex to verify email format
		if (!email.matches("^(.+)@(.+)$")) {
			throw new CMSException(INVALID_EMAIL, email);
		}
	}
}
