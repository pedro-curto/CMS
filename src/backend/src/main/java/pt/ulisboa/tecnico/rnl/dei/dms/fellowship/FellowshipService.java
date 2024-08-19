package pt.ulisboa.tecnico.rnl.dei.dms.fellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.error.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.domain.EvaluationCategory;
import pt.ulisboa.tecnico.rnl.dei.dms.evaluation.repository.EvaluationCategoryRepository;
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
    @Autowired
    private EvaluationCategoryRepository evaluationCategoryRepository;

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
        // saves default evaluation categories from the fellowship on the repository
        fellowship.getWeights().keySet().forEach(category -> {
            if (!evaluationCategoryRepository.existsByName(category)) {
                evaluationCategoryRepository.save(new EvaluationCategory(category));
            }
        });
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }

    @Transactional
    public FellowshipDto updateFellowship(Long id, FellowshipDto fellowshipDto) {
        Fellowship fellowship = fellowshipRepository.findById(id)
                .orElseThrow(() -> new CMSException(FELLOWSHIP_NOT_FOUND));
        // checks if all categories exist in the repository
        Map<String, Double> newWeights = fellowshipDto.getWeights();
        if (newWeights != null) {
            // Verify if all categories exist in the repository
            newWeights.keySet().forEach(category -> {
                if (!evaluationCategoryRepository.existsByName(category)) {
                    throw new CMSException(EVALUATION_CATEGORY_NOT_FOUND, category);
                }
            });
        }
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
        // verifies if all categories exist in the repository
        weights.keySet().forEach(category -> {
            if (!evaluationCategoryRepository.existsByName(category)) {
                // creates the category if it doesn't exist
                evaluationCategoryRepository.save(new EvaluationCategory(category));
            }
        });
        // checks if their sum is 1
        double sum = weights.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sum != 1) {
            throw new CMSException(WRONG_WEIGHTS_SUM, sum);
        }
        fellowship.setWeights(weights);
        fellowshipRepository.save(fellowship);
        return new FellowshipDto(fellowship);
    }
}
