package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorResponse;
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
    public ResponseEntity<FellowshipDto> getFellowship(@PathVariable Long id) {
        try {
            FellowshipDto fellowshipDto = fellowshipService.getFellowship(id);
            return new ResponseEntity<>(fellowshipDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFellowship(@Valid @RequestBody FellowshipDto fellowshipDto) {
        try {
            FellowshipDto result = fellowshipService.addFellowship(fellowshipDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid input", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFellowship(@PathVariable Long id, @Valid @RequestBody FellowshipDto fellowshipDto) {
        try {
            FellowshipDto result = fellowshipService.updateFellowship(id, fellowshipDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid input", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFellowship(@PathVariable Long id) {
        try {
            fellowshipService.deleteFellowship(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateWeights/{id}")
    public ResponseEntity<?> updateFellowshipWeights(@PathVariable Long id, @RequestBody Map<String, Double> weights) {
        try {
            FellowshipDto updatedFellowship = fellowshipService.updateFellowshipWeights(id, weights);
            return new ResponseEntity<>(updatedFellowship, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid input", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
