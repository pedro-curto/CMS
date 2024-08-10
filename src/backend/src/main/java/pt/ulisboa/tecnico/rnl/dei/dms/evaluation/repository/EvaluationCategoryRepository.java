package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;

@Repository
@Transactional
public interface EvaluationCategoryRepository extends JpaRepository<EvaluationCategory, Long> {
	boolean existsByName(String name);
}
