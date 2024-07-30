package pt.ulisboa.tecnico.rnl.dei.dms.materials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.domain.Material;

@Repository
@Transactional
public interface MaterialRepository extends JpaRepository<Material, Long> {}
