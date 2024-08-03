	package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

	import jakarta.persistence.*;
	import jakarta.validation.constraints.NotNull;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
	import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationDto;

	import java.util.EnumMap;
	import java.util.Map;

	@Entity
	// guarantee that
	@Table(name = "evaluation",
			uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id"}))
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class Evaluation {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@OneToOne
		@JoinColumn(name = "enrollment_id")
		@NotNull
		private Enrollment enrollment;
		@ElementCollection
		private Map<EvaluationCategory, Double> scores = new EnumMap<>(EvaluationCategory.class);
		//@ElementCollection
		//private Map<EvaluationCategory, Double> weights = new EnumMap<>(EvaluationCategory.class);

		public Evaluation(Enrollment enrollment, EvaluationDto evaluationDto) {
			setEnrollment(enrollment);
			this.scores = evaluationDto.getScores();
			//this.weights = evaluationDto.getWeights();
		}

		public void setEnrollment(Enrollment enrollment) {
			this.enrollment = enrollment;
			this.enrollment.addEvaluation(this);
		}

		//public Double calculateFinalScore() {
		//	double finalScore = 0;
		//	for (EvaluationCategory category : scores.keySet()) {
		//		finalScore += scores.get(category) * weights.get(category);
		//	}
		//	return finalScore;
		//}

	}
