package pt.ulisboa.tecnico.rnl.dei.dms.materials.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.materials.dto.MaterialDto;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MaterialType type;

    private boolean available;

    public Material() {
    }

    public Material(String name, MaterialType type, boolean available) {
        this.name = name;
        this.type = type;
        this.available = available;
    }

    public Material(MaterialDto materialDto) {
        this.name = materialDto.getName();
        this.type = materialDto.getType();
        this.available = true;
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
