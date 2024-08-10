package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fellowships")
public class FellowshipController {

    @Autowired
    private FellowshipService fellowshipService;

    @GetMapping("/getAll")
    public List<FellowshipDto> getFellowships() {
        return fellowshipService.getFellowships();
    }

    @GetMapping("/get/{id}")
    public FellowshipDto getFellowship(@PathVariable Long id) {
        return fellowshipService.getFellowship(id);
    }

    @PostMapping("/add")
    public FellowshipDto addFellowship(@Valid @RequestBody FellowshipDto fellowshipDto) {
        return fellowshipService.addFellowship(fellowshipDto);
    }

    @PutMapping("/update/{id}")
    public FellowshipDto updateFellowship(@PathVariable Long id, @Valid @RequestBody FellowshipDto fellowshipDto) {
       return fellowshipService.updateFellowship(id, fellowshipDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<FellowshipDto> deleteFellowship(@PathVariable Long id) {
        return fellowshipService.deleteFellowship(id);
    }

    @PutMapping("/updateWeights/{id}")
    public FellowshipDto updateFellowshipWeights(@PathVariable Long id, @RequestBody Map<EvaluationCategory, Double> weights) {
        return fellowshipService.updateFellowshipWeights(id, weights);
    }

}
