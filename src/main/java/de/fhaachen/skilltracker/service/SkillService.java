package de.fhaachen.skilltracker.service;

import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.repository.SkillRepository;
import de.fhaachen.skilltracker.service.dto.SkillDTO;
import de.fhaachen.skilltracker.service.mapper.SkillMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Interface for managing {@link de.fhaachen.skilltracker.domain.Skill}.
 */
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    /**
     * Partially updates a skill.
     *
     * @param skillDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SkillDTO> partialUpdate(SkillDTO skillDTO) {
        log.debug("Request to partially update skill : {}", skillDTO);

        return skillRepository
            .findById(skillDTO.getId())
            .map(existingskill -> {
                skillMapper.partialUpdate(existingskill, skillDTO);

                return existingskill;
            })
            .map(skillRepository::save)
            .map(skillMapper::toDto);
    }

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all skills");
        return skillRepository.findAll(pageable).map(skillMapper::toDto);
    }

    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SkillDTO> findOne(Long id) {
        log.debug("Request to get skill : {}", id);
        return skillRepository.findById(id).map(skillMapper::toDto);
    }

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete skill : {}", id);
        skillRepository.deleteById(id);
    }
}
