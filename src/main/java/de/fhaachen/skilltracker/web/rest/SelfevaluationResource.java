package de.fhaachen.skilltracker.web.rest;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import de.fhaachen.skilltracker.service.SelfevaluationService;
import de.fhaachen.skilltracker.service.UserService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
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
    private final UserService userService;
    private final SelfevaluationRepository selfevaluationRepository;

    public SelfevaluationResource(
        SelfevaluationService selfevaluationService,
        UserService userService,
        SelfevaluationRepository selfevaluationRepository
    ) {
        this.selfevaluationService = selfevaluationService;
        this.userService = userService;
        this.selfevaluationRepository = selfevaluationRepository;
    }

    /**
     * {@code POST  /selfevaluations} : Create a new selfevaluation.
     *
     * @param selfevaluation the selfevaluation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selfevaluation, or with status {@code 400 (Bad Request)} if the selfevaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/selfevaluations")
    public ResponseEntity<Selfevaluation> createSelfevaluation(@Valid @RequestBody Selfevaluation selfevaluation)
        throws URISyntaxException {
        log.debug("REST request to save Selfevaluation : {}", selfevaluation);
        if (selfevaluation.getId() != null) {
            throw new BadRequestAlertException("A new selfevaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        selfevaluation.setEvaluatingUser(userService.getUserWithAuthorities().get());
        Selfevaluation result = selfevaluationService.save(selfevaluation);
        return ResponseEntity
            .created(new URI("/api/selfevaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /selfevaluations/:id} : Updates an existing selfevaluation.
     *
     * @param id the id of the selfevaluation to save.
     * @param selfevaluation the selfevaluation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selfevaluation,
     * or with status {@code 400 (Bad Request)} if the selfevaluation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selfevaluation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/selfevaluations/{id}")
    public ResponseEntity<Selfevaluation> updateSelfevaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Selfevaluation selfevaluation
    ) throws URISyntaxException {
        log.debug("REST request to update Selfevaluation : {}, {}", id, selfevaluation);
        if (selfevaluation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selfevaluation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selfevaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Selfevaluation result = selfevaluationService.save(selfevaluation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, selfevaluation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /selfevaluations/:id} : Partial updates given fields of an existing selfevaluation, field will ignore if it is null
     *
     * @param id the id of the selfevaluation to save.
     * @param selfevaluation the selfevaluation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selfevaluation,
     * or with status {@code 400 (Bad Request)} if the selfevaluation is not valid,
     * or with status {@code 404 (Not Found)} if the selfevaluation is not found,
     * or with status {@code 500 (Internal Server Error)} if the selfevaluation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/selfevaluations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Selfevaluation> partialUpdateSelfevaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Selfevaluation selfevaluation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Selfevaluation partially : {}, {}", id, selfevaluation);
        if (selfevaluation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, selfevaluation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!selfevaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Selfevaluation> result = selfevaluationService.partialUpdate(selfevaluation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, selfevaluation.getId().toString())
        );
    }

    /**
     * {@code GET  /selfevaluations} : get all the selfevaluations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selfevaluations in body.
     */
    @GetMapping("/selfevaluations")
    public List<Selfevaluation> getAllSelfevaluations() {
        log.debug("REST request to get all Selfevaluations");
        return selfevaluationService.findAll();
    }

    /**
     * {@code GET  /selfevaluations} : get All selfevaluations By CurrentUser.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selfevaluations in body.
     */
    @GetMapping("/selfevaluations/currentuser")
    public List<Selfevaluation> getAllByCurrentUser() {
        log.debug("REST request to get all Selfevaluations by current user");
        return selfevaluationService.findAllByCurrentUser();
    }

    /**
     * {@code GET  /selfevaluations/:id} : get the "id" selfevaluation.
     *
     * @param id the id of the selfevaluation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selfevaluation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/selfevaluations/{id}")
    public ResponseEntity<Selfevaluation> getSelfevaluation(@PathVariable Long id) {
        log.debug("REST request to get Selfevaluation : {}", id);
        Optional<Selfevaluation> selfevaluation = selfevaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(selfevaluation);
    }

    /**
     * {@code DELETE  /selfevaluations/:id} : delete the "id" selfevaluation.
     *
     * @param id the id of the selfevaluation to delete.
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
