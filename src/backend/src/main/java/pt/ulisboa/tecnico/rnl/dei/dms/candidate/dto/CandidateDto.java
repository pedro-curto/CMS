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
	private Long istId;
	private String name;
	private String email;

	public CandidateDto(Long istId, String name, String email) {
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
