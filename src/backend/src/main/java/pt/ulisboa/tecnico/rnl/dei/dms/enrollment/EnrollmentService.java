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
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;

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
	public EnrollmentDto enrollCandidate(Long candidateId, Long fellowshipId, EnrollmentDto enrollmentDto) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

		Enrollment enrollment = new Enrollment(fellowship, candidate, enrollmentDto);
		enrollmentRepository.save(enrollment);

		return new EnrollmentDto(enrollment);
	}

	@Transactional
	public void unenrollCandidate(Long fellowshipId, Long candidateId) {
		Enrollment enrollment = enrollmentRepository.getEnrollmentByFellowshipIdAndCandidateId(fellowshipId, candidateId);
		enrollmentRepository.delete(enrollment);
	}

	@Transactional
	public void deleteEnrollment(Long id) {
		Enrollment enrollment = enrollmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
		enrollmentRepository.delete(enrollment);
	}

	@Transactional
	public List<EnrollmentDto> getEnrollmentsByFellowship(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));

		return fellowship.getEnrollments()
				.stream()
				.map(EnrollmentDto::new)
				.toList();
	}

	@Transactional
	public List<CandidateDto> getEnrolledCandidatesByFellowship(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));

		return fellowship.getEnrollments()
				.stream()
				.map(enrollment -> new CandidateDto(candidateRepository.findById(enrollment.getCandidate().getId()).orElseThrow()))
				.collect(Collectors.toList());
	}

	@Transactional
	public List<FellowshipDto> getCandidateFellowships(Long candidateId) {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

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
	public List<EnrollmentDto> getFellowshipEnrollments(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));

		return fellowship.getEnrollments()
				.stream()
				.map(EnrollmentDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public Long getEnrollmentId(Long fellowshipId, Long candidateId) {
		Enrollment enrollment = enrollmentRepository.getEnrollmentByFellowshipIdAndCandidateId(fellowshipId, candidateId);
		System.out.println("EnrollmentId: " + enrollment.getId());
		return enrollment.getId();
	}
}
