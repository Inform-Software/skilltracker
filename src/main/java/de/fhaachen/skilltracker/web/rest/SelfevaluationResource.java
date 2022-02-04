package de.fhaachen.skilltracker.web.rest;

import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import de.fhaachen.skilltracker.service.SelfevaluationService;
import de.fhaachen.skilltracker.service.dto.SelfevaluationDTO;
import de.fhaachen.skilltracker.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.fhaachen.skilltracker.domain.Selfevaluation}.
 */
@RestController
@RequestMapping("/api")
public class SelfevaluationResource {

    private final Logger log = LoggerFactory.getLogger(SelfevaluationResource.class);

    private static final String ENTITY_NAME = "selfevaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SelfevaluationService selfevaluationService;

    private final SelfevaluationRepository selfevaluationRepository;

    public SelfevaluationResource(SelfevaluationService selfevaluationService, SelfevaluationRepository selfevaluationRepository) {
        this.selfevaluationService = selfevaluationService;
        this.selfevaluationRepository = selfevaluationRepository;
    }

    /**
     * {@code POST  /selfevaluations} : Create a new selfevaluation.
     *
     * @param selfevaluationDTO the selfevaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selfevaluationDTO, or with status {@code 400 (Bad Request)} if the selfevaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/selfevaluations")
    public ResponseEntity<SelfevaluationDTO> createSelfevaluation(@Valid @RequestBody SelfevaluationDTO selfevaluationDTO)
        throws URISyntaxException {
        log.debug("REST request to save Selfevaluation : {}", selfevaluationDTO);
        if (selfevaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new selfevaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelfevaluationDTO result = selfevaluationService.save(selfevaluationDTO);
        return ResponseEntity
            .created(new URI("/api/selfevaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /selfevaluations/:id} : Updates an existing selfevaluation.
     *
     * @param id the id of the selfevaluationDTO to save.
     * @param selfevaluationDTO the selfevaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selfevaluationDTO,
     * or with status {@code 400 (Bad Request)} if the selfevaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selfevaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/selfevaluations/{id}")
    public ResponseEntity<SelfevaluationDTO> updateSelfevaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SelfevaluationDTO selfevaluationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Selfevaluation : {}, {}", id, selfevaluationDTO);
        if (selfevaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selfevaluationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selfevaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SelfevaluationDTO result = selfevaluationService.save(selfevaluationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, selfevaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /selfevaluations/:id} : Partial updates given fields of an existing selfevaluation, field will ignore if it is null
     *
     * @param id the id of the selfevaluationDTO to save.
     * @param selfevaluationDTO the selfevaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selfevaluationDTO,
     * or with status {@code 400 (Bad Request)} if the selfevaluationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the selfevaluationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the selfevaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/selfevaluations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SelfevaluationDTO> partialUpdateSelfevaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SelfevaluationDTO selfevaluationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Selfevaluation partially : {}, {}", id, selfevaluationDTO);
        if (selfevaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selfevaluationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selfevaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SelfevaluationDTO> result = selfevaluationService.partialUpdate(selfevaluationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, selfevaluationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /selfevaluations} : get all the selfevaluations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selfevaluations in body.
     */
    @GetMapping("/selfevaluations")
    public ResponseEntity<List<SelfevaluationDTO>> getAllSelfevaluations(Pageable pageable) {
        log.debug("REST request to get a page of Selfevaluations");
        Page<SelfevaluationDTO> page = selfevaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /selfevaluations/:id} : get the "id" selfevaluation.
     *
     * @param id the id of the selfevaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selfevaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/selfevaluations/{id}")
    public ResponseEntity<SelfevaluationDTO> getSelfevaluation(@PathVariable Long id) {
        log.debug("REST request to get Selfevaluation : {}", id);
        Optional<SelfevaluationDTO> selfevaluationDTO = selfevaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(selfevaluationDTO);
    }

    /**
     * {@code DELETE  /selfevaluations/:id} : delete the "id" selfevaluation.
     *
     * @param id the id of the selfevaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/selfevaluations/{id}")
    public ResponseEntity<Void> deleteSelfevaluation(@PathVariable Long id) {
        log.debug("REST request to delete Selfevaluation : {}", id);
        selfevaluationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
