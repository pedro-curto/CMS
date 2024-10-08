package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.util.List;
import java.util.Map;

@Service
public class EvaluationService {
	@Autowired
	private EvaluationRepository evaluationRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private FellowshipRepository fellowshipRepository;

	@Transactional
	public EvaluationDto createEvaluation(EvaluationDto evaluationDto) {
		Enrollment enrollment = enrollmentRepository.findById(evaluationDto.getEnrollmentId())
				.orElseThrow(() -> new CMSException(ENROLLMENT_NOT_FOUND, evaluationDto.getEnrollmentId()));
		// ensures that an evaluation does not already exist for the enrollment
		if (evaluationRepository.existsByEnrollmentId(enrollment.getId())) {
			throw new CMSException(EVALUATION_ALREADY_EXISTS);
		}
		Evaluation evaluation = new Evaluation(enrollment, evaluationDto);
		evaluationRepository.save(evaluation);
		return new EvaluationDto(evaluation);
	}

	public EvaluationDto updateEvaluation(EvaluationDto evaluationDto) {
		Evaluation evaluation = evaluationRepository.findById(evaluationDto.getId())
				.orElseThrow(() -> new CMSException(EVALUATION_NOT_FOUND, evaluationDto.getId()));
		evaluation.update(evaluationDto);
		evaluationRepository.save(evaluation);
		return new EvaluationDto(evaluation);
	}

	@Transactional
	public Double getCandidateFinalEvaluation(Long enrollmentId) {
		if (!evaluationRepository.existsByEnrollmentId(enrollmentId)) {
			throw new CMSException(EVALUATION_NOT_FOUND, enrollmentId);
		}
		Evaluation evaluation = evaluationRepository.getEvaluationByEnrollmentId(enrollmentId);
		return evaluation.calculateFinalScore();
	}

	@Transactional
	public EvaluationDto getEvaluationDetails(Long enrollmentId) {
		Evaluation evaluation = evaluationRepository.getEvaluationByEnrollmentId(enrollmentId);
		if (evaluation == null) {
			throw new CMSException(EVALUATION_NOT_FOUND, enrollmentId);
		}
		return new EvaluationDto(evaluation);
	}

	@Transactional
	public Map<String, Double> getEvaluationWeights(Long fellowshipId) {
		// weights belong to the fellowship itself, not to the evaluation
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, fellowshipId));
		return fellowship.getWeights();
	}

	@Transactional
	public List<EvaluationDto> getEvaluations() {
		return evaluationRepository.findAll().stream().map(EvaluationDto::new).toList();
	}

	@Transactional
	// fetches the winner of the fellowship
	public CandidateDto getFellowshipWinner(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, fellowshipId));
		List<Enrollment> enrollments = fellowship.getEnrollments();
		if (enrollments.isEmpty()) {
			throw new CMSException(NO_ENROLLMENTS_FOUND);
		}
		Double highestScore = 0.0;
		Enrollment winner = null;
		for (Enrollment enrollment : enrollments) {
			// all candidates must be evaluated
			if (!evaluationRepository.existsByEnrollmentId(enrollment.getId())) {
				throw new CMSException(EVALUATION_NOT_FOUND, enrollment.getId());
			}
			// gets maximum score
			Evaluation evaluation = evaluationRepository.getEvaluationByEnrollmentId(enrollment.getId());
			Double finalScore = evaluation.calculateFinalScore();
			if (finalScore > highestScore) {
				highestScore = finalScore;
				winner = enrollment;
			}
		}
		if (winner == null) {
			throw new CMSException(NO_WINNER_FOUND);
		}
		fellowship.setWinnerId(winner.getCandidate().getId());
		fellowship.closeFellowship();
		return new CandidateDto(winner.getCandidate());
	}
}
