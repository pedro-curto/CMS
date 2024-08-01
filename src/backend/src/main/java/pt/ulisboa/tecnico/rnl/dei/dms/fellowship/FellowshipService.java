package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FellowshipService {

    @Autowired
    private FellowshipRepository fellowshipRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    public List<FellowshipDto> getFellowships() {
        return fellowshipRepository.findAll()
                .stream()
                .map(FellowshipDto::new)
                .collect(Collectors.toList());
    }

    public FellowshipDto getFellowship(Long id) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fellowship not found"));
        return new FellowshipDto(fellowship);
    }

    public FellowshipDto addFellowship(FellowshipDto fellowshipDto) {
        Fellowship fellowship = new Fellowship(fellowshipDto);
        // adds candidates to the fellowship
        if (fellowshipDto.getCandidates() != null) {
            for (CandidateDto candidateDto : fellowshipDto.getCandidates()) {
                Candidate candidate = candidateRepository.findByIstId(candidateDto.getIstId())
                        .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
                fellowship.addCandidate(candidate);
            }
        }
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

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

    public void deleteFellowship(Long id) {
        if (!fellowshipRepository.existsById(id)) {
            throw new IllegalArgumentException("Fellowship not found");
        }
        fellowshipRepository.deleteById(id);
    }
    
}
