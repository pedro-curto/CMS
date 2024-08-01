package pt.ulisboa.tecnico.rnl.dei.dms.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.repository.CandidateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(CandidateDto::new)
                .collect(Collectors.toList());
    }

    public CandidateDto addCandidate(CandidateDto candidateDto) {
        Candidate candidate = new Candidate(candidateDto);
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    public CandidateDto updateCandidate(Long id, CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }
    
}
