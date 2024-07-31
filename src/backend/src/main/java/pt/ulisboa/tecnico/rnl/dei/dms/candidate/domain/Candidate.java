package pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.dto.MaterialDto;

@Entity
@Table(name = "candidate")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;

	public Candidate() {}

	public Candidate(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Candidate(CandidateDto candidateDto) {
		this.name = candidateDto.getName();
		this.email = candidateDto.getEmail();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
