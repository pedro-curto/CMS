package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FellowshipService {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    public List<FellowshipDto> getFellowships() {
        return fellowshipRepository.findAll()
                .stream()
                .map(FellowshipDto::new)
                .collect(Collectors.toList());
    }

    public FellowshipDto addFellowship(FellowshipDto FellowshipDto) {
        Fellowship Fellowship = new Fellowship(FellowshipDto);
        fellowshipRepository.save(Fellowship);
        return new FellowshipDto(Fellowship);
    }

    public FellowshipDto updateFellowship(Long id, FellowshipDto FellowshipDto) {
        Fellowship Fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
        Fellowship.setFellowshipId(FellowshipDto.getFellowshipId());
        Fellowship.setStartDate(FellowshipDto.getStartDate());
        Fellowship.setEndDate(FellowshipDto.getEndDate());
        Fellowship.setMonthlyValue(FellowshipDto.getMonthlyValue());
        fellowshipRepository.save(Fellowship);
        return new FellowshipDto(Fellowship);
    }

    public void deleteFellowship(Long id) {
        if (!fellowshipRepository.existsById(id)) {
            throw new IllegalArgumentException("Fellowship not found");
        }
        fellowshipRepository.deleteById(id);
    }
    
}
