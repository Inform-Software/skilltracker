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
    private User evaluatinguser;

    @ManyToOne(optional = false)
    @NotNull
    private Skill evaluatedskill;

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

    public User getEvaluatinguser() {
        return this.evaluatinguser;
    }

    public void setEvaluatinguser(User user) {
        this.evaluatinguser = user;
    }

    public Selfevaluation evaluatinguser(User user) {
        this.setEvaluatinguser(user);
        return this;
    }

    public Skill getEvaluatedskill() {
        return this.evaluatedskill;
    }

    public void setEvaluatedskill(Skill skill) {
        this.evaluatedskill = skill;
    }

    public Selfevaluation evaluatedskill(Skill skill) {
        this.setEvaluatedskill(skill);
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
