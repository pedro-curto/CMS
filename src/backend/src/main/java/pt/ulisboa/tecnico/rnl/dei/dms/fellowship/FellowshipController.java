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

    @GetMapping("/get/{id}")
    public FellowshipDto getFellowship(@PathVariable Long id) {
        return fellowshipService.getFellowship(id);
    }

    @PostMapping("/add")
    public FellowshipDto addFellowship(@Valid @RequestBody FellowshipDto FellowshipDto) {
        return fellowshipService.addFellowship(FellowshipDto);
    }

    @PutMapping("/update/{id}")
    public FellowshipDto updateFellowship(@PathVariable Long id, @Valid @RequestBody FellowshipDto fellowshipDto) {
        return fellowshipService.updateFellowship(id, fellowshipDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFellowship(@PathVariable Long id) {
        try {
            fellowshipService.deleteFellowship(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
