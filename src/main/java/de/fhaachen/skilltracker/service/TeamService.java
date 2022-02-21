package de.fhaachen.skilltracker.service;

import de.fhaachen.skilltracker.domain.Team;
import de.fhaachen.skilltracker.domain.User;
import de.fhaachen.skilltracker.repository.TeamRepository;
import de.fhaachen.skilltracker.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        return teamRepository.save(team);
    }

    /**
     * Partially update a team.
     *
     * @param team the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Team> partialUpdate(Team team) {
        log.debug("Request to partially update Team : {}", team);

        return teamRepository
            .findById(team.getId())
            .map(existingTeam -> {
                if (team.getName() != null) {
                    existingTeam.setName(team.getName());
                }

                return existingTeam;
            })
            .map(teamRepository::save);
    }

    /**
     * Get all the teams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        log.debug("Request to get all Teams");
        return teamRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the teams with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Team> findAllWithEagerRelationships(Pageable pageable) {
        return teamRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Team> findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Get team by user login.
     *
     * @param login the login of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public List<Team> findByUser(String login) {
        log.debug("Request to get Team by User login : {}", login);
        return teamRepository.findByUser(login);
    }

    /**
     * Delete the team by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
    }

    /**
     * remove user from team.
     *
     * @param userLogin, teamId
     * @return the persisted entity.
     */
    public Optional<Team> removeUser(String userLogin, Long teamId) {
        log.debug("Request to remove User from a Team : {} , {}", teamId, userLogin);
        return teamRepository
            .findById(teamId)
            .map(existingTeam -> {
                for (User user : existingTeam.getTeamMembers()) {
                    if (userLogin.equals(user.getLogin())) {
                        existingTeam.removeTeamMember(user);
                        break;
                    }
                }
                return existingTeam;
            })
            .map(teamRepository::save);
    }

    /**
     * add user to team.
     *
     * @param userLogin, teamId
     * @return the persisted entity.
     */
    public Optional<Team> addUser(String userLogin, Long teamId) {
        log.debug("Request to add User from a Team : {} , {}", teamId, userLogin);
        Optional<Team> team = teamRepository.findById(teamId);
        team.ifPresent(value -> value.addTeamMember(userRepository.findOneByLogin(userLogin).get()));
        return team.map(teamRepository::save);
    }
}
