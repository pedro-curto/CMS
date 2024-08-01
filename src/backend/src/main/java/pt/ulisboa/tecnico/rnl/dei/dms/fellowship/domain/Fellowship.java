package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
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
	private String fellowshipId;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal monthlyValue;
	@ManyToMany
	private List<Candidate> candidates = new ArrayList<>();

	public Fellowship() {}

	public Fellowship(String fellowshipId, LocalDate startDate, LocalDate endDate, BigDecimal monthlyValue) {
		this.fellowshipId = fellowshipId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.monthlyValue = monthlyValue;
	}

	public Fellowship(FellowshipDto fellowshipDto) {
		this.fellowshipId = fellowshipDto.getFellowshipId();
		this.startDate = fellowshipDto.getStartDate();
		this.endDate = fellowshipDto.getEndDate();
		this.monthlyValue = fellowshipDto.getMonthlyValue();
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

	public List<Candidate> getCandidates() {
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

	public void addCandidate(Candidate candidate) {
		candidates.add(candidate);
	}

	public void removeCandidate(Candidate candidate) {
		candidates.remove(candidate);
	}

}
