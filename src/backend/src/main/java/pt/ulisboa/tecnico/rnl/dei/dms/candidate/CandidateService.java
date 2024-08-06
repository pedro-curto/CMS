package pt.ulisboa.tecnico.rnl.dei.dms.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Transactional
    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(CandidateDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CandidateDto getCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CMSException(CANDIDATE_NOT_FOUND, id));
        return new CandidateDto(candidate);
    }

    @Transactional
    public CandidateDto addCandidate(CandidateDto candidateDto) {
        // checks if istID already exists
        Optional<Candidate> candidateCheck = candidateRepository.findByIstId(candidateDto.getIstId());
        if (candidateCheck.isPresent()) {
            throw new CMSException(IST_ID_ALREADY_EXISTS, candidateDto.getIstId());
        }
        Candidate candidate = new Candidate(candidateDto);
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    @Transactional
    public CandidateDto updateCandidate(Long id, CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CMSException(CANDIDATE_NOT_FOUND, id));
        // checks if istID already exists and if it is different from the current candidate
        Optional<Candidate> candidateCheck = candidateRepository.findByIstId(candidateDto.getIstId());
        if (candidateCheck.isPresent() && !candidateCheck.get().getId().equals(id)) {
            throw new CMSException(IST_ID_ALREADY_EXISTS, candidateDto.getIstId());
        }
        candidate.update(candidateDto);
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    @Transactional
    public List<CandidateDto> deleteCandidate(Long id) {
        if (!candidateRepository.existsById(id)) {
            throw new CMSException(CANDIDATE_NOT_FOUND, id);
        }
        candidateRepository.deleteById(id);
        return getCandidates();
    }
    
}
