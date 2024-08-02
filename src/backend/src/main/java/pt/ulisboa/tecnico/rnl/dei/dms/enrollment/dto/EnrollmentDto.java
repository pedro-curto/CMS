package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

// simplifies getters, setters and default constructors
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
	private Long id;
	private String motivation;
	private String enrollmentDateTime;
	private Long fellowshipId;
	private Long candidateId;

	public EnrollmentDto(Enrollment enrollment) {
		this.id = enrollment.getId();
		this.motivation = enrollment.getMotivation();
		this.enrollmentDateTime = enrollment.getEnrollmentDateTime();
		this.fellowshipId = enrollment.getFellowship().getId();
		this.candidateId = enrollment.getCandidate().getId();
	}

}
