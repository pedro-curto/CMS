package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;

import java.util.EnumMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDto {
	private Long id;
	private Long enrollmentId;
	private Map<EvaluationCategory, Double> scores = new EnumMap<>(EvaluationCategory.class);
	//private Map<EvaluationCategory, Double> weights = new EnumMap<>(EvaluationCategory.class);

	public EvaluationDto(Evaluation evaluation) {
		this.id = evaluation.getId();
		this.enrollmentId = evaluation.getEnrollment().getId();
		this.scores = evaluation.getScores();
		//this.weights = evaluation.getWeights();
	}

}
