package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto;

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
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal monthlyValue;
	private boolean closed;
	private Long winnerId;

	private Map<EvaluationCategory, Double> weights;

	public FellowshipDto(Fellowship fellowship) {
		this.id = fellowship.getId();
		this.name = fellowship.getName();
		this.description = fellowship.getDescription();
		this.startDate = fellowship.getStartDate();
		this.endDate = fellowship.getEndDate();
		this.monthlyValue = fellowship.getMonthlyValue();
		this.closed = fellowship.isClosed();
		this.winnerId = fellowship.getWinnerId();
		this.weights = fellowship.getWeights();
	}

}
