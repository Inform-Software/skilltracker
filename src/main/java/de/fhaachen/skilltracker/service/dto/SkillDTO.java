package de.fhaachen.skilltracker.service.dto;

import de.fhaachen.skilltracker.domain.enumeration.SkillCategory;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.fhaachen.skilltracker.domain.Skill} entity.
 */
public class SkillDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 70)
    private String name;

    @NotNull
    private SkillCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillDTO)) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
