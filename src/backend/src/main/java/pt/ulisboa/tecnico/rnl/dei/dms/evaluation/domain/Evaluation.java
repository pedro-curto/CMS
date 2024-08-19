	package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

	import jakarta.persistence.*;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
	import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
	import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationDto;
	import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

	import java.util.HashMap;
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
		private Enrollment enrollment;
		@ElementCollection
		// its value must be between 0 and 20
		private Map<String, Double> scores = new HashMap<>();

		public Evaluation(Enrollment enrollment, EvaluationDto evaluationDto) {
			setEnrollment(enrollment);
			this.scores = evaluationDto.getScores();
			enrollmentNotNull();
			// checks if the scores are between 0 and 20
			validateScores();
		}

		public void setEnrollment(Enrollment enrollment) {
			this.enrollment = enrollment;
			this.enrollment.addEvaluation(this);
		}

		public Double calculateFinalScore() {
			// gets weights from the fellowship
			Map<String, Double> weights = enrollment.getFellowship().getWeights();
			double finalScore = 0;
			// calculates the final score
			for (String category : scores.keySet()) {
				finalScore += scores.get(category) * weights.get(category);
			}
			return finalScore;
		}

		private void enrollmentNotNull() {
			if (enrollment == null) {
				throw new CMSException(ENROLLMENT_REQUIRED);
			}
		}

		private void validateScores() {
			// guarantee that scores exist
			if (scores == null) {
				throw new CMSException(SCORES_REQUIRED);
			}
			// scores can't be empty
			if (scores.isEmpty()) {
				throw new CMSException(SCORES_CANT_BE_EMPTY);
			}
			// scores must be between 0 and 20
			for (String category : scores.keySet()) {
				if (scores.get(category) < 0 || scores.get(category) > 20) {
					throw new CMSException(SCORES_OUT_OF_BOUNDS);
				}
			}

		}

	}
