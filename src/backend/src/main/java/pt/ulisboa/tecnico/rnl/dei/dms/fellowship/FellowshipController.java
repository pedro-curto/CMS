package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;

import java.util.List;

@RestController
@RequestMapping("/fellowships")
public class FellowshipController {

    @Autowired
    private FellowshipService fellowshipService;

    @GetMapping("/getAll")
    public List<FellowshipDto> getFellowships() {
        return fellowshipService.getFellowships();
    }

    @PostMapping("/add")
    public FellowshipDto addFellowship(@Valid @RequestBody FellowshipDto FellowshipDto) {
        return fellowshipService.addFellowship(FellowshipDto);
    }

}
