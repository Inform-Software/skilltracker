package de.fhaachen.skilltracker.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.fhaachen.skilltracker.IntegrationTest;
import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.domain.enumeration.SkillCategory;
import de.fhaachen.skilltracker.repository.SkillRepository;
import de.fhaachen.skilltracker.service.dto.SkillDTO;
import de.fhaachen.skilltracker.service.mapper.SkillMapper;
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
 * Integration tests for the {@link SkillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillResourceIT {

    private static final SkillCategory DEFAULT_CATEGORY = SkillCategory.TECHNOLOGIE_ALLGEMEIN;
    private static final SkillCategory UPDATED_CATEGORY = SkillCategory.VDS_V10;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillMockMvc;

    private Skill skill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill().category(DEFAULT_CATEGORY).name(DEFAULT_NAME);
        return skill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createUpdatedEntity(EntityManager em) {
        Skill skill = new Skill().category(UPDATED_CATEGORY).name(UPDATED_NAME);
        return skill;
    }

    @BeforeEach
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();
        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);
        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSkill.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSkillWithExistingId() throws Exception {
        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setCategory(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setName(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
        em.detach(updatedSkill);
        updatedSkill.category(UPDATED_CATEGORY).name(UPDATED_NAME);
        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);

        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill.name(UPDATED_NAME);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill.category(UPDATED_CATEGORY).name(UPDATED_NAME);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Delete the skill
        restSkillMockMvc
            .perform(delete(ENTITY_API_URL_ID, skill.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
