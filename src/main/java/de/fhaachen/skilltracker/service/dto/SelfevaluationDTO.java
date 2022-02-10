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

    private UserDTO evaluatinguser;

    private SkillDTO evaluatedskill;

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

    public UserDTO getEvaluatinguser() {
        return evaluatinguser;
    }

    public void setEvaluatinguser(UserDTO evaluatinguser) {
        this.evaluatinguser = evaluatinguser;
    }

    public SkillDTO getEvaluatedskill() {
        return evaluatedskill;
    }

    public void setEvaluatedskill(SkillDTO evaluatedskill) {
        this.evaluatedskill = evaluatedskill;
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
            ", evaluatinguser=" + getEvaluatinguser() +
            ", evaluatedskill=" + getEvaluatedskill() +
            "}";
    }
}
