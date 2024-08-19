package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCategoryDto {
	private Long id;
	private String name;

	public EvaluationCategoryDto(EvaluationCategory evaluationCategory) {
		this.id = evaluationCategory.getId();
		this.name = evaluationCategory.getName();
	}
}