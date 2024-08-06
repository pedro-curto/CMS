package pt.ulisboa.tecnico.rnl.dei.dms.enrollment;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	@PostMapping("/enroll")
	public EnrollmentDto enrollCandidate(@Valid @RequestBody EnrollmentDto enrollmentDto) {
		return enrollmentService.enrollCandidate(enrollmentDto);
	}

	@DeleteMapping("/delete/{id}")
	public List<EnrollmentDto> deleteEnrollment(@PathVariable Long id) {
		return enrollmentService.deleteEnrollment(id);
	}

	@GetMapping("/fellowships/{fellowshipId}/candidates")
	public List<CandidateDto> getEnrolledCandidatesByFellowship(@PathVariable Long fellowshipId) {
		return enrollmentService.getEnrolledCandidatesByFellowship(fellowshipId);
	}

	@GetMapping("/candidates/{candidateId}/fellowships")
	public List<FellowshipDto> getCandidateFellowships(@PathVariable Long candidateId) {
		return enrollmentService.getCandidateFellowships(candidateId);
	}

	@GetMapping("/get")
	public List<EnrollmentDto> getEnrollments() {
		return enrollmentService.getEnrollments();
	}

	@GetMapping("/getId")
	public Long getEnrollmentId(@RequestParam Long fellowshipId, @RequestParam Long candidateId) {
		return enrollmentService.getEnrollmentId(fellowshipId, candidateId);
	}

	@GetMapping("/fellowships/{fellowshipId}")
	public List<EnrollmentDto> getFellowshipEnrollments(@PathVariable Long fellowshipId) {
		return enrollmentService.getFellowshipEnrollments(fellowshipId);
	}

}
