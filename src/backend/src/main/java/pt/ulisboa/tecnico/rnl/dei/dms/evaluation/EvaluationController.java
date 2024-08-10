package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

	@Autowired
	private EvaluationService evaluationService;

	@PostMapping("/create")
	public EvaluationDto createEvaluation(@Valid @RequestBody EvaluationDto evaluationDto) {
		return evaluationService.createEvaluation(evaluationDto);
	}

	@GetMapping("/getFinalEvaluation/{enrollmentId}")
	public Double getCandidateFinalEvaluation(@PathVariable Long enrollmentId) {
		return evaluationService.getCandidateFinalEvaluation(enrollmentId);
	}

	@GetMapping("/getDetails/{enrollmentId}")
	public EvaluationDto getEvaluationDetails(@PathVariable Long enrollmentId) {
		return evaluationService.getEvaluationDetails(enrollmentId);
	}

	@GetMapping("/getWeights/{fellowshipId}")
	public Map<EvaluationCategory, Double> getEvaluationWeights(@PathVariable Long fellowshipId) {
		return evaluationService.getEvaluationWeights(fellowshipId);
	}

	@GetMapping("/getAll")
	public List<EvaluationDto> getEvaluations() {
		return evaluationService.getEvaluations();
	}

	@GetMapping("/getWinner/{fellowshipId}")
	public CandidateDto getFellowshipWinner(@PathVariable Long fellowshipId) {
		return evaluationService.getFellowshipWinner(fellowshipId);
	}

}
