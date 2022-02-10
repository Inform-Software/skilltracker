package de.fhaachen.skilltracker.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Selfevaluation.
 */
@Entity
@Table(name = "selfevaluation")
public class Selfevaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne(optional = false)
    @NotNull
    private Skill evaluatedSkill;

    @ManyToOne(optional = false)
    @NotNull
    private User evaluatingUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Selfevaluation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return this.value;
    }

    public Selfevaluation value(Integer value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Skill getEvaluatedSkill() {
        return this.evaluatedSkill;
    }

    public void setEvaluatedSkill(Skill skill) {
        this.evaluatedSkill = skill;
    }

    public Selfevaluation evaluatedSkill(Skill skill) {
        this.setEvaluatedSkill(skill);
        return this;
    }

    public User getEvaluatingUser() {
        return this.evaluatingUser;
    }

    public void setEvaluatingUser(User user) {
        this.evaluatingUser = user;
    }

    public Selfevaluation evaluatingUser(User user) {
        this.setEvaluatingUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Selfevaluation)) {
            return false;
        }
        return id != null && id.equals(((Selfevaluation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Selfevaluation{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
