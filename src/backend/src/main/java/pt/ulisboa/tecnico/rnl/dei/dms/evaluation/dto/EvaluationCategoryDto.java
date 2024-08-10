package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import lombok.Data;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;

@Data
public class EvaluationCategoryDto {
	private Long id;
	private String name;

	public EvaluationCategoryDto(EvaluationCategory evaluationCategory) {
		this.id = evaluationCategory.getId();
		this.name = evaluationCategory.getName();
	}
}
