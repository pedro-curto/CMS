package pt.ulisboa.tecnico.rnl.dei.dms.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollment.domain.Enrollment;

@Repository
@Transactional
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	@Query("SELECT e FROM Enrollment e WHERE e.fellowship.id = :fellowshipId AND e.candidate.id = :candidateId")
	Enrollment getEnrollmentByFellowshipIdAndCandidateId(Long fellowshipId, Long candidateId);
}
