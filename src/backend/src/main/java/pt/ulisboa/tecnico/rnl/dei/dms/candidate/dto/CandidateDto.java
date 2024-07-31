package pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;

public class CandidateDto {
	private Long id;
	private String name;
	private String email;

	public CandidateDto() {
	}

	public CandidateDto(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public CandidateDto(Candidate candidate) {
		this.id = candidate.getId();
		this.name = candidate.getName();
		this.email = candidate.getEmail();
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

	@Override
	public String toString() {
		return "CandidateDto {id=" + id + ", name=" + name + ", email=" + email + "}";
	}

}
