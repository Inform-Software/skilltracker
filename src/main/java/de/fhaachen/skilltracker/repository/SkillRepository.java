package de.fhaachen.skilltracker.repository;

import de.fhaachen.skilltracker.domain.Skill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {}
