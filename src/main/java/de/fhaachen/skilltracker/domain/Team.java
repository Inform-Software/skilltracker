package de.fhaachen.skilltracker.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "rel_team__team_of_user",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "team_of_user_id")
    )
    private Set<User> teamOfUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Team name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getTeamOfUsers() {
        return this.teamOfUsers;
    }

    public void setTeamOfUsers(Set<User> users) {
        this.teamOfUsers = users;
    }

    public Team teamOfUsers(Set<User> users) {
        this.setTeamOfUsers(users);
        return this;
    }

    public Team addTeamOfUser(User user) {
        this.teamOfUsers.add(user);
        return this;
    }

    public Team removeTeamOfUser(User user) {
        this.teamOfUsers.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
