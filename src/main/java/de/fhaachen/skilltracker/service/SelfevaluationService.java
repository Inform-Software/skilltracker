package de.fhaachen.skilltracker.service;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import de.fhaachen.skilltracker.service.dto.SelfevaluationDTO;
import de.fhaachen.skilltracker.service.mapper.SelfevaluationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final SelfevaluationMapper selfevaluationMapper;

    public SelfevaluationService(SelfevaluationRepository selfevaluationRepository, SelfevaluationMapper selfevaluationMapper) {
        this.selfevaluationRepository = selfevaluationRepository;
        this.selfevaluationMapper = selfevaluationMapper;
    }

    /**
     * Save a selfevaluation.
     *
     * @param selfevaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public SelfevaluationDTO save(SelfevaluationDTO selfevaluationDTO) {
        log.debug("Request to save Selfevaluation : {}", selfevaluationDTO);
        Selfevaluation selfevaluation = selfevaluationMapper.toEntity(selfevaluationDTO);
        selfevaluation = selfevaluationRepository.save(selfevaluation);
        return selfevaluationMapper.toDto(selfevaluation);
    }

    /**
     * Partially update a selfevaluation.
     *
     * @param selfevaluationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SelfevaluationDTO> partialUpdate(SelfevaluationDTO selfevaluationDTO) {
        log.debug("Request to partially update Selfevaluation : {}", selfevaluationDTO);

        return selfevaluationRepository
            .findById(selfevaluationDTO.getId())
            .map(existingSelfevaluation -> {
                selfevaluationMapper.partialUpdate(existingSelfevaluation, selfevaluationDTO);

                return existingSelfevaluation;
            })
            .map(selfevaluationRepository::save)
            .map(selfevaluationMapper::toDto);
    }

    /**
     * Get all the selfevaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SelfevaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Selfevaluations");
        return selfevaluationRepository.findAll(pageable).map(selfevaluationMapper::toDto);
    }

    /**
     * Get one selfevaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SelfevaluationDTO> findOne(Long id) {
        log.debug("Request to get Selfevaluation : {}", id);
        return selfevaluationRepository.findById(id).map(selfevaluationMapper::toDto);
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
