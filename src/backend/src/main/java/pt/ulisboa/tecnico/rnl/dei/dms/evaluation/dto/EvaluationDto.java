package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDto {
	private Long id;
	private Long enrollmentId;
	private Map<String, Double> scores = new HashMap<>();

	public EvaluationDto(Evaluation evaluation) {
		this.id = evaluation.getId();
		this.enrollmentId = evaluation.getEnrollment().getId();
		this.scores = evaluation.getScores();
	}

}
