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
		@NotNull(message = "Enrollment ID is required")
		private Enrollment enrollment;
		@ElementCollection
		// its value must be between 0 and 20
		private Map<EvaluationCategory, Double> scores = new EnumMap<>(EvaluationCategory.class);

		public Evaluation(Enrollment enrollment, EvaluationDto evaluationDto) {
			setEnrollment(enrollment);
			this.scores = evaluationDto.getScores();
			// checks if the scores are between 0 and 20
			validateScores();
		}

		public void setEnrollment(Enrollment enrollment) {
			this.enrollment = enrollment;
			this.enrollment.addEvaluation(this);
		}

		public Double calculateFinalScore() {
			// gets weights from the fellowship
			Map<EvaluationCategory, Double> weights = enrollment.getFellowship().getWeights();
			double finalScore = 0;
			for (EvaluationCategory category : scores.keySet()) {
				finalScore += scores.get(category) * weights.get(category);
			}
			return finalScore;
		}

		private void validateScores() {
			for (EvaluationCategory category : scores.keySet()) {
				if (scores.get(category) < 0 || scores.get(category) > 20) {
					throw new IllegalArgumentException("Scores must be between 0 and 20");
				}
			}
		}

	}
