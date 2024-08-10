package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationCategoryDto;

import java.util.List;

@RestController
@RequestMapping("/evaluation-category")
public class EvaluationCategoryController {

	@Autowired
	private EvaluationCategoryService evaluationCategoryService;

	@GetMapping("/getAll")
	public List<EvaluationCategoryDto> getEvaluationCategories() {
		return evaluationCategoryService.getEvaluationCategories();
	}

	@PostMapping("/create")
	public EvaluationCategoryDto createEvaluationCategory(EvaluationCategoryDto evaluationCategoryDto) {
		return evaluationCategoryService.createEvaluationCategory(evaluationCategoryDto);
	}

	@PutMapping("/update")
	public EvaluationCategoryDto updateEvaluationCategory(EvaluationCategoryDto evaluationCategoryDto) {
		return evaluationCategoryService.updateEvaluationCategory(evaluationCategoryDto);
	}

	@DeleteMapping("/delete/{id}")
	public List<EvaluationCategoryDto> deleteEvaluationCategory(@PathVariable Long id) {
		return evaluationCategoryService.deleteEvaluationCategory(id);
	}

}
