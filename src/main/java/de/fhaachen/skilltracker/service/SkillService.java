package de.fhaachen.skilltracker.service;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.domain.User;
import de.fhaachen.skilltracker.domain.enumeration.SkillCategory;
import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import de.fhaachen.skilltracker.repository.SkillRepository;
import de.fhaachen.skilltracker.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
@Transactional
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;

    private final UserRepository userRepository;

    private final SelfevaluationRepository selfevaluationRepository;

    public SkillService(SkillRepository skillRepository, UserRepository userRepository, SelfevaluationRepository selfevaluationRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.selfevaluationRepository = selfevaluationRepository;
    }

    /**
     * Save a skill.
     *
     * @param skill the entity to save.
     * @return the persisted entity.
     */
    public Skill save(Skill skill) {
        log.debug("Request to save Skill : {}", skill);
        Skill save = skillRepository.save(skill);
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Selfevaluation selfevaluation = new Selfevaluation();
            selfevaluation.setValue(0);
            selfevaluation.setIsEvaluated(false);
            selfevaluation.setWantToImprove(false);
            selfevaluation.setEvaluatingUser(user);
            selfevaluation.setEvaluatedSkill(skill);
            selfevaluationRepository.save(selfevaluation);
        }
        return save;
    }

    /**
     * Partially update a skill.
     *
     * @param skill the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Skill> partialUpdate(Skill skill) {
        log.debug("Request to partially update Skill : {}", skill);

        return skillRepository
            .findById(skill.getId())
            .map(existingSkill -> {
                if (skill.getCategory() != null) {
                    existingSkill.setCategory(skill.getCategory());
                }
                if (skill.getName() != null) {
                    existingSkill.setName(skill.getName());
                }

                return existingSkill;
            })
            .map(skillRepository::save);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll();
    }

    /**
     * Get one skill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Skill> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }

    /**
     * find By Category.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Skill> findSkillsByCategory(SkillCategory category) {
        log.debug("Request to get all skills by category");
        return skillRepository.findSkillsByCategory(category);
    }
}
