package pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;

@Data
@NoArgsConstructor
public class CandidateDto {
	private Long id;
	@NotBlank
	private String istId;
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;

	public CandidateDto(String istId, String name, String email) {
		this.istId = istId;
		this.name = name;
		this.email = email;
	}

	public CandidateDto(Candidate candidate) {
		this.id = candidate.getId();
		this.istId = candidate.getIstId();
		this.name = candidate.getName();
		this.email = candidate.getEmail();
	}

}
