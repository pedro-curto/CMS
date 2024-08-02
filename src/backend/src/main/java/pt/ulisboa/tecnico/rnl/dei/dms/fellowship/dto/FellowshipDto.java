package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FellowshipDto {
	private Long id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal monthlyValue;
	private List<CandidateDto> candidates;

	public FellowshipDto() {}

	public FellowshipDto(String name, String description, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
	}

	public FellowshipDto(Fellowship fellowship) {
		this.id = fellowship.getId();
		this.name = fellowship.getName();
		this.description = fellowship.getDescription();
		this.startDate = fellowship.getStartDate();
		this.endDate = fellowship.getEndDate();
		this.monthlyValue = fellowship.getMonthlyValue();
		this.candidates = new ArrayList<>();
		for (Candidate candidate : fellowship.getCandidates()) {
			this.candidates.add(new CandidateDto(candidate));
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public BigDecimal getMonthlyValue() {
		return monthlyValue;
	}

	public List<CandidateDto> getCandidates() {
		return candidates;
	}

	public void setId(Long id) { this.id = id; }

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setMonthlyValue(BigDecimal monthlyValue) {
		this.monthlyValue = monthlyValue;
	}

	public void setCandidates(List<CandidateDto> candidates) {
		this.candidates = candidates;
	}

	@Override
	public String toString() {
		return "FellowshipDto {id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", monthlyValue=" + monthlyValue +
				", candidates=" + candidates + "}";
	}

}
