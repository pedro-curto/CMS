package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationCategoryRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
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
	private Map<EvaluationCategory, Double> weights = new HashMap<>();

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

	private Map<EvaluationCategory, Double> initializeDefaultWeights() {
		// initializes three default categories (Curriculum, Interview, Exercise) and gives them weights
		Map<EvaluationCategory, Double> defaultWeights = new HashMap<>();
		EvaluationCategory curriculum = new EvaluationCategory("Curriculum");
		EvaluationCategory exercise = new EvaluationCategory("Exercise");
		EvaluationCategory interview = new EvaluationCategory("Interview");
		defaultWeights.put(curriculum, 0.3);
		defaultWeights.put(exercise, 0.3);
		defaultWeights.put(interview, 0.4);
		return defaultWeights;
	}

	public void updateWeight(EvaluationCategory category, Double weight) {
		if (weights.containsKey(category)) {
			weights.put(category, weight);
		} else {
			throw new CMSException(CATEGORY_NOT_FOUND);
		}
	}

	public void update(FellowshipDto fellowshipDto) {
		setName(fellowshipDto.getName());
		setDescription(fellowshipDto.getDescription());
		setStartDate(fellowshipDto.getStartDate());
		setEndDate(fellowshipDto.getEndDate());
		setMonthlyValue(fellowshipDto.getMonthlyValue());
		setWeights(fellowshipDto.getWeights() != null ? fellowshipDto.getWeights() : initializeDefaultWeights());
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
		double sum = 0;
		for (Map.Entry<EvaluationCategory, Double> entry : weights.entrySet()) {
			sum += entry.getValue();
		}
		if (sum != 1) {
			throw new CMSException(WRONG_WEIGHTS_SUM, String.valueOf(sum));
		}
	}

	public void closeFellowship() {
		closed = true;
	}
}