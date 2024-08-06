package pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;

@Repository
@Transactional
public interface FellowshipRepository extends JpaRepository<Fellowship, Long> {}
