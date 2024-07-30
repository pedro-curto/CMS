package pt.ulisboa.tecnico.rnl.dei.dms.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.dto.MaterialDto;
import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<MaterialDto>
    getMaterials() {
      return materialService.getMaterials();
    }

    @PostMapping("/create")
    public MaterialDto createMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.createMaterial(materialDto);
    }

    @PutMapping("/update")
    public List<MaterialDto> updateMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.updateMaterial(materialDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<MaterialDto> deleteMaterial(@PathVariable Long id) {
        return materialService.deleteMaterial(id);
    }

}
