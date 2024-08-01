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
	private String fellowshipId;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal monthlyValue;
	private List<CandidateDto> candidates;

	public FellowshipDto() {}

	public FellowshipDto(String fellowshipId, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.fellowshipId = fellowshipId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
	}

	public FellowshipDto(Fellowship fellowship) {
		this.id = fellowship.getId();
		this.fellowshipId = fellowship.getFellowshipId();
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

	public String getFellowshipId() {
		return fellowshipId;
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

	public void setFellowshipId(String fellowshipId) {
		this.fellowshipId = fellowshipId;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setCandidates(List<CandidateDto> candidates) {
		this.candidates = candidates;
	}

	@Override
	public String toString() {
		return "FellowshipDto {id=" + id + ", fellowshipId=" + fellowshipId + ", startDate=" + startDate + ", endDate=" + endDate + ", monthlyValue=" + monthlyValue + "}";
	}

}
