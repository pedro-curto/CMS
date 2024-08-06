package pt.ulisboa.tecnico.rnl.dei.dms.candidate;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/getAll")
    public List<CandidateDto> getCandidates() {
        return candidateService.getCandidates();
    }

    @GetMapping("/get/{id}")
    public CandidateDto getCandidate(@PathVariable Long id) {
        return candidateService.getCandidate(id);
    }

    @PostMapping("/add")
    public CandidateDto addCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.addCandidate(candidateDto);
    }

    @PutMapping("/update/{id}")
    public CandidateDto updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto candidateDto) {
        return candidateService.updateCandidate(id, candidateDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<CandidateDto> deleteCandidate(@PathVariable Long id) {
        return candidateService.deleteCandidate(id);
    }

}
