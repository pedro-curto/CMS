package pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.Evaluation;

@Repository
@Transactional
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
	Evaluation getEvaluationByEnrollmentId(Long enrollmentId);
	boolean existsByEnrollmentId(Long enrollmentId);
}
