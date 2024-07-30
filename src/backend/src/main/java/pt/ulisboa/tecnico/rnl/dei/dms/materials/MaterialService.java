package pt.ulisboa.tecnico.rnl.dei.dms.materials;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.materials.domain.Material;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.dto.MaterialDto;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.repository.MaterialRepository;


@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    
    public MaterialDto createMaterial(MaterialDto materialDto) {
        Material material = new Material(materialDto);
        materialRepository.save(material);
        return new MaterialDto(material);
    }

    public List<MaterialDto> getMaterials() {
        return materialRepository.findAll().stream().map(MaterialDto::new).collect(Collectors.toList());
    }

    public List<MaterialDto> updateMaterial(MaterialDto materialDto) {
        Material material = materialRepository.findById(materialDto.getId()).get();
        material.setName(materialDto.getName());
        material.setType(materialDto.getType());
        material.setAvailable(materialDto.isAvailable());
        materialRepository.save(material);
        return getMaterials();
    }

    public List<MaterialDto> deleteMaterial(Long id) {
        materialRepository.deleteById(id);
        return getMaterials();
    }

    
}
