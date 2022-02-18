package de.fhaachen.skilltracker.repository;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.domain.enumeration.SkillCategory;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select distinct (skill) from Skill skill where skill.category=:category")
    List<Skill> findSkillsByCategory(@Param("category") SkillCategory category);
}
