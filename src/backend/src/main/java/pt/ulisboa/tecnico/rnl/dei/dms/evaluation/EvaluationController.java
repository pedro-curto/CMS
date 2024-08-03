package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<EvaluationDto> createEvaluation(@RequestBody EvaluationDto evaluationDto) {
		try {
			EvaluationDto result = evaluationService.createEvaluation(evaluationDto);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getFinalEvaluation/{enrollmentId}")
	public ResponseEntity<Double> getCandidateFinalEvaluation(@PathVariable Long enrollmentId) {
		try {
			Double result = evaluationService.getCandidateFinalEvaluation(enrollmentId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDetails/{enrollmentId}")
	public ResponseEntity<EvaluationDto> getEvaluationDetails(@PathVariable Long enrollmentId) {
		try {
			EvaluationDto result = evaluationService.getEvaluationDetails(enrollmentId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getCategories")
	public ResponseEntity<List<EvaluationCategory>> getEvaluationCategories() {
		try {
			List<EvaluationCategory> result = evaluationService.getEvaluationCategories();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getWeights/{fellowshipId}")
	public ResponseEntity<Map<EvaluationCategory, Double>> getEvaluationWeights(@PathVariable Long fellowshipId) {
		try {
			Map<EvaluationCategory, Double> result = evaluationService.getEvaluationWeights(fellowshipId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAll")
	public List<EvaluationDto> getEvaluations() {
		return evaluationService.getEvaluations();
	}

	@GetMapping("/getWinner/{fellowshipId}")
	public ResponseEntity<CandidateDto> getFellowshipWinner(@PathVariable Long fellowshipId) {
		try {
			CandidateDto result = evaluationService.getFellowshipWinner(fellowshipId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
