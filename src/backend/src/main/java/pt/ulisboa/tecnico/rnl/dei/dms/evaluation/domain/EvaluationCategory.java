package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.dto.EvaluationCategoryDto;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.NAME_REQUIRED;

@Entity
@Table(name = "evaluation_category")
@Data
@NoArgsConstructor
public class EvaluationCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	public EvaluationCategory(EvaluationCategoryDto evaluationCategoryDto) {
		this.name = evaluationCategoryDto.getName();
		verifyInvariants();
	}

	public EvaluationCategory(String curriculum) {
		this.name = curriculum;
		verifyInvariants();
	}

	public void update(EvaluationCategoryDto evaluationCategoryDto) {
		this.name = evaluationCategoryDto.getName();
		verifyInvariants();
	}

	private void verifyInvariants() {
		nameIsRequired();
	}

	private void nameIsRequired() {
		if (name == null || name.isBlank()) {
			throw new CMSException(NAME_REQUIRED);
		}
	}
}