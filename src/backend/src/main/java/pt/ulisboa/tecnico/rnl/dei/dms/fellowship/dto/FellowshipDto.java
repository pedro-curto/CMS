package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FellowshipDto {
	private Long id;
	@NotBlank(message = "Fellowship name is mandatory")
	@Size(min = 3, max = 25, message = "Fellowship name must be between 3 and 25 characters")
	private String name;

	@Size(max = 255, message = "Fellowship description must be less than 255 characters")
	private String description;

	@NotNull(message = "Start date is mandatory")
	private LocalDate startDate;

	@NotNull(message = "End date is mandatory")
	private LocalDate endDate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Fellowship value must be greater than 0")
	@NotNull(message = "Monthly value is mandatory")
	private BigDecimal monthlyValue;

	private Map<EvaluationCategory, Double> weights;

	public FellowshipDto(Fellowship fellowship) {
		this.id = fellowship.getId();
		this.name = fellowship.getName();
		this.description = fellowship.getDescription();
		this.startDate = fellowship.getStartDate();
		this.endDate = fellowship.getEndDate();
		this.monthlyValue = fellowship.getMonthlyValue();
		this.weights = fellowship.getWeights();
	}

}
