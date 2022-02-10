package de.fhaachen.skilltracker.service;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Selfevaluation}.
 */
@Service
@Transactional
public class SelfevaluationService {

    private final Logger log = LoggerFactory.getLogger(SelfevaluationService.class);

    private final SelfevaluationRepository selfevaluationRepository;

    public SelfevaluationService(SelfevaluationRepository selfevaluationRepository) {
        this.selfevaluationRepository = selfevaluationRepository;
    }

    /**
     * Save a selfevaluation.
     *
     * @param selfevaluation the entity to save.
     * @return the persisted entity.
     */
    public Selfevaluation save(Selfevaluation selfevaluation) {
        log.debug("Request to save Selfevaluation : {}", selfevaluation);
        return selfevaluationRepository.save(selfevaluation);
    }

    /**
     * Partially update a selfevaluation.
     *
     * @param selfevaluation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Selfevaluation> partialUpdate(Selfevaluation selfevaluation) {
        log.debug("Request to partially update Selfevaluation : {}", selfevaluation);

        return selfevaluationRepository
            .findById(selfevaluation.getId())
            .map(existingSelfevaluation -> {
                if (selfevaluation.getValue() != null) {
                    existingSelfevaluation.setValue(selfevaluation.getValue());
                }

                return existingSelfevaluation;
            })
            .map(selfevaluationRepository::save);
    }

    /**
     * Get all the selfevaluations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Selfevaluation> findAll() {
        log.debug("Request to get all Selfevaluations");
        return selfevaluationRepository.findAll();
    }

    /**
     * Get one selfevaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Selfevaluation> findOne(Long id) {
        log.debug("Request to get Selfevaluation : {}", id);
        return selfevaluationRepository.findById(id);
    }

    /**
     * Delete the selfevaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Selfevaluation : {}", id);
        selfevaluationRepository.deleteById(id);
    }
}
