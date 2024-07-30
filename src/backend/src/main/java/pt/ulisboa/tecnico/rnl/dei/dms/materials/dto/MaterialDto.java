package pt.ulisboa.tecnico.rnl.dei.dms.materials.dto;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.domain.Material;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.domain.MaterialType;


public class MaterialDto {
    private Long id;
    private String name;
    
    @Enumerated(EnumType.STRING)
    private MaterialType type;

    private boolean available;

    public MaterialDto() {
    }

    public MaterialDto(String name, MaterialType type, boolean available) {
        this.name = name;
        this.type = type;
        this.available = available;
    }

    public MaterialDto(Material material) {
        this.id = material.getId();
        this.name = material.getName();
        this.type = material.getType();
        this.available = material.isAvailable();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MaterialType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", available=" + available +
                '}';
    }
    
}
