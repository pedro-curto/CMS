package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "fellowship")
@Data
@NoArgsConstructor
public class Fellowship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Fellowship name is mandatory")
	@Size(min=3, max=25, message = "Fellowship name must be between 3 and 25 characters")
	private String name;
	@Size(max=255, message = "Fellowship description must be less than 255 characters")
	private String description;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	@DecimalMin(value="0.0", inclusive=false, message = "Fellowship value must be greater than 0")
	@NotNull
	private BigDecimal monthlyValue;
	@OneToMany(mappedBy = "fellowship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enrollment> enrollments = new ArrayList<>();
	@ElementCollection
	private Map<EvaluationCategory, Double> weights = new EnumMap<>(EvaluationCategory.class);

	public Fellowship(String name, String description, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
		// if weights are provided, use them; else, use default weights
		this.weights = initializeDefaultWeights();
	}

	public Fellowship(FellowshipDto fellowshipDto) {
		this.name = fellowshipDto.getName();
		this.description = fellowshipDto.getDescription();
		this.startDate = fellowshipDto.getStartDate();
		this.endDate = fellowshipDto.getEndDate();
		this.monthlyValue = fellowshipDto.getMonthlyValue();
		this.weights = fellowshipDto.getWeights() != null ? fellowshipDto.getWeights()
				: initializeDefaultWeights();
	}

	public void addEnrollment(Enrollment enrollment) {
		enrollments.add(enrollment);
	}

	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
	}

	private Map<EvaluationCategory, Double> initializeDefaultWeights() {
		Map<EvaluationCategory, Double> defaultWeights = new EnumMap<>(EvaluationCategory.class);
		defaultWeights.put(EvaluationCategory.Curriculum, 0.5);
		defaultWeights.put(EvaluationCategory.Interview, 0.3);
		defaultWeights.put(EvaluationCategory.Exercise, 0.2);
		return defaultWeights;
	}

	public void updateWeight(EvaluationCategory category, Double weight) {
		if (weights.containsKey((category))) {
			weights.put(category, weight);
		} else {
			throw new IllegalArgumentException("Category not found");
		}
	}

	public void updateWeights(Map<String, Double> weights) {
		weights.forEach((category, weight) -> {
			EvaluationCategory evaluationCategory = EvaluationCategory.valueOf(category);
			updateWeight(evaluationCategory, weight);
		});
	}
}
