package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FellowshipService {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Transactional
    public List<FellowshipDto> getFellowships() {
        return fellowshipRepository.findAll()
                .stream()
                .map(FellowshipDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FellowshipDto getFellowship(Long id) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public FellowshipDto addFellowship(FellowshipDto fellowshipDto) {
        Fellowship fellowship = new Fellowship(fellowshipDto);
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public FellowshipDto updateFellowship(Long id, FellowshipDto fellowshipDto) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
        fellowship.setName(fellowshipDto.getName());
        fellowship.setStartDate(fellowshipDto.getStartDate());
        fellowship.setEndDate(fellowshipDto.getEndDate());
        fellowship.setMonthlyValue(fellowshipDto.getMonthlyValue());
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public void deleteFellowship(Long id) {
        if (!fellowshipRepository.existsById(id)) {
            throw new IllegalArgumentException("Fellowship not found");
        }
        fellowshipRepository.deleteById(id);
    }
    
}
