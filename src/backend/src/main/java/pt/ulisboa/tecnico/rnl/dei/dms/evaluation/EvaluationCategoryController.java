package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import jakarta.validation.Valid;
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
	public EvaluationCategoryDto createEvaluationCategory(@RequestBody @Valid EvaluationCategoryDto evaluationCategoryDto) {
		return evaluationCategoryService.createEvaluationCategory(evaluationCategoryDto);
	}

	@PutMapping("/update")
	public EvaluationCategoryDto updateEvaluationCategory(@RequestBody @Valid EvaluationCategoryDto evaluationCategoryDto) {
		return evaluationCategoryService.updateEvaluationCategory(evaluationCategoryDto);
	}

	@DeleteMapping("/delete/{name}")
	public List<EvaluationCategoryDto> deleteEvaluationCategory(@PathVariable String name) {
		return evaluationCategoryService.deleteEvaluationCategory(name);
	}
}