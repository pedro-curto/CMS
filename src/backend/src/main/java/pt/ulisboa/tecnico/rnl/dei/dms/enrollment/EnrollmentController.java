package pt.ulisboa.tecnico.rnl.dei.dms.enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<EnrollmentDto> enrollCandidate(@RequestBody EnrollmentDto enrollmentDto) {
		try {
			EnrollmentDto result = enrollmentService.enrollCandidate(
					enrollmentDto.getCandidateId(),
					enrollmentDto.getFellowshipId(),
					enrollmentDto);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/unenroll")
	public ResponseEntity<Void> unenrollCandidate(@RequestParam Long fellowshipId, @RequestParam Long candidateId) {
		try {
			enrollmentService.unenrollCandidate(fellowshipId, candidateId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
		System.out.println("EnrollmentId: " + id);
		try {
			enrollmentService.deleteEnrollment(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fellowships/{fellowshipId}/candidates")
	public ResponseEntity<List<CandidateDto>> getEnrolledCandidatesByFellowship(@PathVariable Long fellowshipId) {
		try {
			List<CandidateDto> candidates = enrollmentService.getEnrolledCandidatesByFellowship(fellowshipId);
			return ResponseEntity.ok(candidates);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/candidates/{candidateId}/fellowships")
	public ResponseEntity<List<FellowshipDto>> getCandidateFellowships(@PathVariable Long candidateId) {
		try {
			List<FellowshipDto> fellowships = enrollmentService.getCandidateFellowships(candidateId);
			return ResponseEntity.ok(fellowships);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<List<EnrollmentDto>> getEnrollments() {
		return ResponseEntity.ok(enrollmentService.getEnrollments());
	}

	@GetMapping("/fellowships/{fellowshipId}")
	public ResponseEntity<List<EnrollmentDto>> getFellowshipEnrollments(@PathVariable Long fellowshipId) {
		return ResponseEntity.ok(enrollmentService.getFellowshipEnrollments(fellowshipId));
	}


}
