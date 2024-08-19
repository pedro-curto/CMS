package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "fellowship")
@Data
@NoArgsConstructor
public class Fellowship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal monthlyValue;
	private boolean closed = false;
	private Long winnerId;

	@OneToMany(mappedBy = "fellowship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Enrollment> enrollments = new ArrayList<>();

	@ElementCollection
	private Map<String, Double> weights = new HashMap<>();

	public Fellowship(String name, String description, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
		this.weights = initializeDefaultWeights();
	}

	public Fellowship(FellowshipDto fellowshipDto) {
		this.name = fellowshipDto.getName();
		this.description = fellowshipDto.getDescription();
		this.startDate = fellowshipDto.getStartDate();
		this.endDate = fellowshipDto.getEndDate();
		this.monthlyValue = fellowshipDto.getMonthlyValue();
		this.weights = fellowshipDto.getWeights() != null ? fellowshipDto.getWeights() : initializeDefaultWeights();
		verifyInvariants();
		validateDates();
		verifyWeights();
	}

	public void addEnrollment(Enrollment enrollment) {
		enrollments.add(enrollment);
	}

	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
	}

	private Map<String, Double> initializeDefaultWeights() {
		Map<String, Double> defaultWeights = new HashMap<>();
		defaultWeights.put("Curriculum", 0.3);
		defaultWeights.put("Interview", 0.5);
		defaultWeights.put("Exercise", 0.2);
		return defaultWeights;
	}

	public void addEvaluationCategory(String category, Double weight) {
		if (weights.containsKey(category)) {
			throw new CMSException(CATEGORY_ALREADY_EXISTS, category);
		}
		weights.put(category, weight);
		adjustWeights();
	}

	public void removeEvaluationCategory(String category) {
		if (!weights.containsKey(category)) {
			throw new CMSException(CATEGORY_NOT_FOUND);
		}
		weights.remove(category);
		adjustWeights();
	}

	private void adjustWeights() {
		double totalWeight = weights.values().stream().mapToDouble(Double::doubleValue).sum();
		weights.replaceAll((category, weight) -> weight / totalWeight);
	}

	public void updateWeight(String category, Double weight) {
		if (!weights.containsKey(category)) {
			throw new CMSException(CATEGORY_NOT_FOUND);
		}
		weights.put(category, weight);
		adjustWeights();
	}

	public void update(FellowshipDto fellowshipDto) {
		setName(fellowshipDto.getName());
		setDescription(fellowshipDto.getDescription());
		setStartDate(fellowshipDto.getStartDate());
		setEndDate(fellowshipDto.getEndDate());
		setMonthlyValue(fellowshipDto.getMonthlyValue());
		setWeights(fellowshipDto.getWeights());
		verifyInvariants();
		validateDates();
		verifyWeights();
	}

	private void verifyInvariants() {
		nameIsRequired();
		nameBetweenTwoAndTwentyFiveCharacters();
		descriptionLessThanTwoHundredFiftyFiveCharacters();
		startDateIsRequired();
		endDateIsRequired();
		monthlyValueIsRequired();
		monthlyValueGreaterThanZero();
	}

	private void nameIsRequired() {
		if (name == null || name.isBlank()) {
			throw new CMSException(FELLOWSHIP_NAME_REQUIRED);
		}
	}

	private void nameBetweenTwoAndTwentyFiveCharacters() {
		if (name.length() < 3 || name.length() > 25) {
			throw new CMSException(FELLOWSHIP_NAME_SIZE);
		}
	}

	private void descriptionLessThanTwoHundredFiftyFiveCharacters() {
		if (description != null && description.length() > 255) {
			throw new CMSException(FELLOWSHIP_DESCRIPTION_SIZE);
		}
	}

	private void startDateIsRequired() {
		if (startDate == null) {
			throw new CMSException(FELLOWSHIP_START_DATE_REQUIRED);
		}
	}

	private void endDateIsRequired() {
		if (endDate == null) {
			throw new CMSException(FELLOWSHIP_END_DATE_REQUIRED);
		}
	}

	private void monthlyValueIsRequired() {
		if (monthlyValue == null) {
			throw new CMSException(FELLOWSHIP_MONTHLY_VALUE_REQUIRED);
		}
	}

	private void monthlyValueGreaterThanZero() {
		if (monthlyValue.compareTo(BigDecimal.ZERO) <= 0) {
			throw new CMSException(FELLOWSHIP_VALUE_MIN);
		}
	}

	private void validateDates() {
		if (startDate.isAfter(endDate)) {
			throw new CMSException(START_DATE_BEFORE_END_DATE);
		}
	}

	private void verifyWeights() {
		double sum = weights.values().stream().mapToDouble(Double::doubleValue).sum();
		if (sum != 1) {
			throw new CMSException(WRONG_WEIGHTS_SUM, String.valueOf(sum));
		}
	}

	public void closeFellowship() {
		closed = true;
	}
}