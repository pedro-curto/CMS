package pt.ulisboa.tecnico.rnl.dei.dms.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate, Long> {}
