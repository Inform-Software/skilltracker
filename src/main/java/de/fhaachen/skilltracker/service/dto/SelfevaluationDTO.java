package de.fhaachen.skilltracker.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link de.fhaachen.skilltracker.domain.Selfevaluation} entity.
 */
public class SelfevaluationDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer value;

    private SkillDTO evaluated_skill;

    private UserDTO evaluating_user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public SkillDTO getEvaluated_skill() {
        return evaluated_skill;
    }

    public void setEvaluated_skill(SkillDTO evaluated_skill) {
        this.evaluated_skill = evaluated_skill;
    }

    public UserDTO getEvaluating_user() {
        return evaluating_user;
    }

    public void setEvaluating_user(UserDTO evaluating_user) {
        this.evaluating_user = evaluating_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SelfevaluationDTO)) {
            return false;
        }

        SelfevaluationDTO selfevaluationDTO = (SelfevaluationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, selfevaluationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SelfevaluationDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", evaluated_skill=" + getEvaluated_skill() +
            ", evaluating_user=" + getEvaluating_user() +
            "}";
    }
}
