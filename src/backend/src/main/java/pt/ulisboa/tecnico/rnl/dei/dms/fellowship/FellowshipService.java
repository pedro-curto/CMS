package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.domain.Fellowship;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.dto.FellowshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.fellowship.repository.FellowshipRepository;
import static pt.ulisboa.tecnico.rnl.dei.dms.error.ErrorMessage.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FellowshipService {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Transactional
    public List<FellowshipDto> getFellowships() {
        return fellowshipRepository.findAll()
                .stream()
                .map(FellowshipDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FellowshipDto getFellowship(Long id) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, id));
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public FellowshipDto addFellowship(FellowshipDto fellowshipDto) {
        Fellowship fellowship = new Fellowship(fellowshipDto);
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public FellowshipDto updateFellowship(Long id, FellowshipDto fellowshipDto) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND));
        fellowship.update(fellowshipDto);
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public List<FellowshipDto> deleteFellowship(Long id) {
        if (!fellowshipRepository.existsById(id)) {
            throw new CMSException(FELLOWSHIP_NOT_FOUND, id);
        }
        fellowshipRepository.deleteById(id);
        return getFellowships();
    }

    @Transactional
    public FellowshipDto updateFellowshipWeights(Long id, Map<String, Double> weights) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND, id));
        fellowship.updateWeights(weights);
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }
}
