package pt.ulisboa.tecnico.rnl.dei.dms.enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EnrollmentService {

	@Autowired
	private FellowshipRepository fellowshipRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Transactional
	public EnrollmentDto enrollCandidate(EnrollmentDto enrollmentDto) {
		Fellowship fellowship = fellowshipRepository.findById(enrollmentDto.getFellowshipId())
				.orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, enrollmentDto.getFellowshipId()));
		Candidate candidate = candidateRepository.findById(enrollmentDto.getCandidateId())
				.orElseThrow(() -> new CMSException(CANDIDATE_NOT_FOUND, enrollmentDto.getCandidateId()));
		if (fellowship == null) {
			throw new CMSException(FELLOWSHIP_REQUIRED);
		}
		if (candidate == null) {
			throw new CMSException(CANDIDATE_REQUIRED);
		}
		if (enrollmentRepository.getEnrollmentByFellowshipIdAndCandidateId(fellowship.getId(), candidate.getId()) != null) {
			throw new CMSException(ENROLLMENT_ALREADY_EXISTS);
		}
		Enrollment enrollment = new Enrollment(fellowship, candidate, enrollmentDto);
		enrollmentRepository.save(enrollment);

		return new EnrollmentDto(enrollment);
	}

	@Transactional
	public  List<EnrollmentDto> deleteEnrollment(Long id) {
		Enrollment enrollment = enrollmentRepository.findById(id)
				.orElseThrow(() -> new CMSException(ENROLLMENT_NOT_FOUND, id));
		enrollmentRepository.delete(enrollment);
		return getEnrollments();
	}

	@Transactional
	public List<CandidateDto> getEnrolledCandidatesByFellowship(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, fellowshipId));

		return fellowship.getEnrollments()
				.stream()
				.map(enrollment -> new CandidateDto(candidateRepository.findById(enrollment.getCandidate().getId()).orElseThrow()))
				.collect(Collectors.toList());
	}

	@Transactional
	public List<FellowshipDto> getCandidateFellowships(Long candidateId) {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new CMSException(CANDIDATE_NOT_FOUND, candidateId));

		return candidate.getEnrollments()
				.stream()
				.map(enrollment -> new FellowshipDto(fellowshipRepository.findById(enrollment.getFellowship().getId()).orElseThrow()))
				.collect(Collectors.toList());
	}

	@Transactional
	public List<EnrollmentDto> getEnrollments() {
		return enrollmentRepository.findAll()
				.stream()
				.map(EnrollmentDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public Long getEnrollmentId(Long fellowshipId, Long candidateId) {
		Enrollment enrollment = enrollmentRepository.getEnrollmentByFellowshipIdAndCandidateId(fellowshipId, candidateId);
		if (enrollment == null) {
			throw new CMSException(NO_SUCH_ENROLLMENT);
		}
		return enrollment.getId();
	}

	@Transactional
	public List<EnrollmentDto> getFellowshipEnrollments(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, fellowshipId));

		return fellowship.getEnrollments()
				.stream()
				.map(EnrollmentDto::new)
				.collect(Collectors.toList());
	}

}
