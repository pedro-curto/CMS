package pt.ulisboa.tecnico.rnl.dei.dms.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public CandidateDto addCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.addCandidate(candidateDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable Long id, @RequestBody CandidateDto candidateDto) {
        try {
			CandidateDto result = candidateService.updateCandidate(id, candidateDto);
			return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

      return CandidateService.updateCandidate(candidateDto);
    }

}
