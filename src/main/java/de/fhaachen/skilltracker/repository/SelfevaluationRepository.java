package de.fhaachen.skilltracker.repository;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.domain.enumeration.SkillCategory;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Selfevaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelfevaluationRepository extends JpaRepository<Selfevaluation, Long> {
    @Query("select selfevaluation from Selfevaluation selfevaluation where selfevaluation.evaluatingUser.login = ?#{principal.username}")
    List<Selfevaluation> findByEvaluatingUserIsCurrentUser();

    @Query(
        "select selfevaluation from Selfevaluation selfevaluation where selfevaluation.evaluatingUser in (select elements(team.teamMembers) from Team team where team.id =:teamId) and selfevaluation.evaluatedSkill.id =:skillId order by selfevaluation.evaluatingUser.firstName asc "
    )
    List<Selfevaluation> findByTeamAndSkill(@Param("teamId") long teamId, @Param("skillId") long skillId);

    @Query(
        "select selfevaluation from Selfevaluation selfevaluation where selfevaluation.evaluatedSkill.id =:skillId and selfevaluation.isEvaluated = 1 order by selfevaluation.value desc"
    )
    List<Selfevaluation> findBySkillDesc(@Param("skillId") long skillId);
}
