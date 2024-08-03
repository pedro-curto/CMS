package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;

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
				.orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

		// ensures that an evaluation does not already exist for the enrollment
		if (evaluationRepository.existsByEnrollmentId(enrollment.getId())) {
			throw new IllegalArgumentException("Evaluation already exists");
		}
		Evaluation evaluation = new Evaluation(enrollment, evaluationDto);
		evaluationRepository.save(evaluation);
		return new EvaluationDto(evaluation);
	}

	@Transactional
	public Double getCandidateFinalEvaluation(Long enrollmentId) {
		Evaluation evaluation = evaluationRepository.getEvaluationByEnrollmentId(enrollmentId);
		return 2.0;
		//return evaluation.calculateFinalScore();
	}

	@Transactional
	public EvaluationDto getEvaluationDetails(Long enrollmentId) {
		Evaluation evaluation = evaluationRepository.getEvaluationByEnrollmentId(enrollmentId);
		return new EvaluationDto(evaluation);
	}

	@Transactional
	public List<EvaluationCategory> getEvaluationCategories() {
		return List.of(EvaluationCategory.values());
	}

	// weights belong to the fellowship itself, not to the evaluation
	@Transactional
	public Map<EvaluationCategory, Double> getEvaluationWeights(Long fellowshipId) {
		Fellowship fellowship = fellowshipRepository.findById(fellowshipId)
				.orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
		return fellowship.getWeights();
	}

	public List<EvaluationDto> getEvaluations() {
		return evaluationRepository.findAll().stream().map(EvaluationDto::new).toList();
	}
}
