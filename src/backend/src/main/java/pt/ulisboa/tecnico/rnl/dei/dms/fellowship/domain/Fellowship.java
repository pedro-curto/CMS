package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fellowship")
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
	@OneToMany(mappedBy = "fellowship")
	private List<Enrollment> enrollments = new ArrayList<>();

	public Fellowship() {}

	public Fellowship(String name, String description, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
	}

	public Fellowship(FellowshipDto fellowshipDto) {
		this.name = fellowshipDto.getName();
		this.description = fellowshipDto.getDescription();
		this.startDate = fellowshipDto.getStartDate();
		this.endDate = fellowshipDto.getEndDate();
		this.monthlyValue = fellowshipDto.getMonthlyValue();
	}

	public Long getId() {
		return id;
	}

	public String getName() { return name; }
	
	public String getDescription() { return description; }

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public BigDecimal getMonthlyValue() {
		return monthlyValue;
	}

	public List<Enrollment> getEnrollments() { return enrollments; }

	public void setName(String name) { this.name = name; }
	
	public void setDescription(String description) { this.description = description; }

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setMonthlyValue(BigDecimal monthlyValue) {
		this.monthlyValue = monthlyValue;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

	public void addEnrollment(Enrollment enrollment) {
		enrollments.add(enrollment);
	}

	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
	}

}
