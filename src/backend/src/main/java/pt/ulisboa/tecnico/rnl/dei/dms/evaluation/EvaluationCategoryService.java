package pt.ulisboa.tecnico.rnl.dei.dms.evaluation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationCategoryDto;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationCategoryRepository;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.EVALUATION_CATEGORY_ALREADY_EXISTS;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.EVALUATION_CATEGORY_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationCategoryService {
	@Autowired
	private EvaluationCategoryRepository evaluationCategoryRepository;

	@Transactional
	public List<EvaluationCategoryDto> getEvaluationCategories() {
		return evaluationCategoryRepository.findAll()
				.stream()
				.map(EvaluationCategoryDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public EvaluationCategoryDto createEvaluationCategory(EvaluationCategoryDto evaluationCategoryDto) {
		if (evaluationCategoryRepository.existsByName(evaluationCategoryDto.getName())) {
			throw new CMSException(EVALUATION_CATEGORY_ALREADY_EXISTS, evaluationCategoryDto.getName());
		}
		EvaluationCategory evaluationCategory = new EvaluationCategory(evaluationCategoryDto);
		evaluationCategoryRepository.save(evaluationCategory);
		return new EvaluationCategoryDto(evaluationCategory);
	}

	@Transactional
	public EvaluationCategoryDto updateEvaluationCategory(EvaluationCategoryDto evaluationCategoryDto) {
		EvaluationCategory evaluationCategory = evaluationCategoryRepository.findById(evaluationCategoryDto.getId())
				.orElseThrow(() -> new CMSException(EVALUATION_CATEGORY_NOT_FOUND, evaluationCategoryDto.getId()));
		evaluationCategory.update(evaluationCategoryDto);
		evaluationCategoryRepository.save(evaluationCategory);
		return new EvaluationCategoryDto(evaluationCategory);
	}

	@Transactional
	public List<EvaluationCategoryDto> deleteEvaluationCategory(Long id) {
		EvaluationCategory evaluationCategory = evaluationCategoryRepository.findById(id)
				.orElseThrow(() -> new CMSException(EVALUATION_CATEGORY_NOT_FOUND, id));
		evaluationCategoryRepository.delete(evaluationCategory);
		return getEvaluationCategories();
	}

}
