package pt.ulisboa.tecnico.rnl.dei.dms.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidate.dto.CandidateDto;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    
    @Autowired
    private CandidateService CandidateService;

    @GetMapping("/get")
    public List<CandidateDto> getCandidates() {
      return CandidateService.getCandidates();
    }

}
