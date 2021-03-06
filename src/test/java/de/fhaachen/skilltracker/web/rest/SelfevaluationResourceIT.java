package de.fhaachen.skilltracker.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.fhaachen.skilltracker.IntegrationTest;
import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.domain.User;
import de.fhaachen.skilltracker.repository.SelfevaluationRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SelfevaluationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SelfevaluationResourceIT {

    private static final Integer DEFAULT_VALUE = 0;
    private static final Integer UPDATED_VALUE = 1;

    private static final Boolean DEFAULT_WANT_TO_IMPROVE = false;
    private static final Boolean UPDATED_WANT_TO_IMPROVE = true;

    private static final Boolean DEFAULT_IS_EVALUATED = false;
    private static final Boolean UPDATED_IS_EVALUATED = true;

    private static final String ENTITY_API_URL = "/api/selfevaluations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SelfevaluationRepository selfevaluationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSelfevaluationMockMvc;

    private Selfevaluation selfevaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Selfevaluation createEntity(EntityManager em) {
        Selfevaluation selfevaluation = new Selfevaluation()
            .value(DEFAULT_VALUE)
            .wantToImprove(DEFAULT_WANT_TO_IMPROVE)
            .isEvaluated(DEFAULT_IS_EVALUATED);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        selfevaluation.setEvaluatedSkill(skill);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        selfevaluation.setEvaluatingUser(user);
        return selfevaluation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Selfevaluation createUpdatedEntity(EntityManager em) {
        Selfevaluation selfevaluation = new Selfevaluation()
            .value(UPDATED_VALUE)
            .wantToImprove(UPDATED_WANT_TO_IMPROVE)
            .isEvaluated(UPDATED_IS_EVALUATED);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createUpdatedEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        selfevaluation.setEvaluatedSkill(skill);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        selfevaluation.setEvaluatingUser(user);
        return selfevaluation;
    }

    @BeforeEach
    public void initTest() {
        selfevaluation = createEntity(em);
    }

    @Test
    @Transactional
    void createSelfevaluation() throws Exception {
        int databaseSizeBeforeCreate = selfevaluationRepository.findAll().size();
        // Create the Selfevaluation
        restSelfevaluationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isCreated());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeCreate + 1);
        Selfevaluation testSelfevaluation = selfevaluationList.get(selfevaluationList.size() - 1);
        assertThat(testSelfevaluation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSelfevaluation.getWantToImprove()).isEqualTo(DEFAULT_WANT_TO_IMPROVE);
        assertThat(testSelfevaluation.getIsEvaluated()).isEqualTo(DEFAULT_IS_EVALUATED);
    }

    @Test
    @Transactional
    void createSelfevaluationWithExistingId() throws Exception {
        // Create the Selfevaluation with an existing ID
        selfevaluation.setId(1L);

        int databaseSizeBeforeCreate = selfevaluationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelfevaluationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = selfevaluationRepository.findAll().size();
        // set the field null
        selfevaluation.setValue(null);

        // Create the Selfevaluation, which fails.

        restSelfevaluationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSelfevaluations() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        // Get all the selfevaluationList
        restSelfevaluationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selfevaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].wantToImprove").value(hasItem(DEFAULT_WANT_TO_IMPROVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEvaluated").value(hasItem(DEFAULT_IS_EVALUATED.booleanValue())));
    }

    @Test
    @Transactional
    void getSelfevaluation() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        // Get the selfevaluation
        restSelfevaluationMockMvc
            .perform(get(ENTITY_API_URL_ID, selfevaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(selfevaluation.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.wantToImprove").value(DEFAULT_WANT_TO_IMPROVE.booleanValue()))
            .andExpect(jsonPath("$.isEvaluated").value(DEFAULT_IS_EVALUATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSelfevaluation() throws Exception {
        // Get the selfevaluation
        restSelfevaluationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSelfevaluation() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();

        // Update the selfevaluation
        Selfevaluation updatedSelfevaluation = selfevaluationRepository.findById(selfevaluation.getId()).get();
        // Disconnect from session so that the updates on updatedSelfevaluation are not directly saved in db
        em.detach(updatedSelfevaluation);
        updatedSelfevaluation.value(UPDATED_VALUE).wantToImprove(UPDATED_WANT_TO_IMPROVE).isEvaluated(UPDATED_IS_EVALUATED);

        restSelfevaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSelfevaluation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSelfevaluation))
            )
            .andExpect(status().isOk());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
        Selfevaluation testSelfevaluation = selfevaluationList.get(selfevaluationList.size() - 1);
        assertThat(testSelfevaluation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSelfevaluation.getWantToImprove()).isEqualTo(UPDATED_WANT_TO_IMPROVE);
        assertThat(testSelfevaluation.getIsEvaluated()).isEqualTo(UPDATED_IS_EVALUATED);
    }

    @Test
    @Transactional
    void putNonExistingSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, selfevaluation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSelfevaluationWithPatch() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();

        // Update the selfevaluation using partial update
        Selfevaluation partialUpdatedSelfevaluation = new Selfevaluation();
        partialUpdatedSelfevaluation.setId(selfevaluation.getId());

        restSelfevaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSelfevaluation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSelfevaluation))
            )
            .andExpect(status().isOk());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
        Selfevaluation testSelfevaluation = selfevaluationList.get(selfevaluationList.size() - 1);
        assertThat(testSelfevaluation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSelfevaluation.getWantToImprove()).isEqualTo(DEFAULT_WANT_TO_IMPROVE);
        assertThat(testSelfevaluation.getIsEvaluated()).isEqualTo(DEFAULT_IS_EVALUATED);
    }

    @Test
    @Transactional
    void fullUpdateSelfevaluationWithPatch() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();

        // Update the selfevaluation using partial update
        Selfevaluation partialUpdatedSelfevaluation = new Selfevaluation();
        partialUpdatedSelfevaluation.setId(selfevaluation.getId());

        partialUpdatedSelfevaluation.value(UPDATED_VALUE).wantToImprove(UPDATED_WANT_TO_IMPROVE).isEvaluated(UPDATED_IS_EVALUATED);

        restSelfevaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSelfevaluation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSelfevaluation))
            )
            .andExpect(status().isOk());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
        Selfevaluation testSelfevaluation = selfevaluationList.get(selfevaluationList.size() - 1);
        assertThat(testSelfevaluation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSelfevaluation.getWantToImprove()).isEqualTo(UPDATED_WANT_TO_IMPROVE);
        assertThat(testSelfevaluation.getIsEvaluated()).isEqualTo(UPDATED_IS_EVALUATED);
    }

    @Test
    @Transactional
    void patchNonExistingSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, selfevaluation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSelfevaluation() throws Exception {
        int databaseSizeBeforeUpdate = selfevaluationRepository.findAll().size();
        selfevaluation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSelfevaluationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(selfevaluation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Selfevaluation in the database
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSelfevaluation() throws Exception {
        // Initialize the database
        selfevaluationRepository.saveAndFlush(selfevaluation);

        int databaseSizeBeforeDelete = selfevaluationRepository.findAll().size();

        // Delete the selfevaluation
        restSelfevaluationMockMvc
            .perform(delete(ENTITY_API_URL_ID, selfevaluation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Selfevaluation> selfevaluationList = selfevaluationRepository.findAll();
        assertThat(selfevaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
