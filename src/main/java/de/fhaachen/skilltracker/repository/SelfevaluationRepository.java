package de.fhaachen.skilltracker.repository;

import de.fhaachen.skilltracker.domain.Selfevaluation;
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
        "SELECT S.NAME AS SKILLNAME, VALUE, WANT_TO_IMPROVE AS IMPROVE, CONCAT(U.FIRST_NAME, ', ',U.LAST_NAME) AS USERNAME, T.NAME  AS TEAMNAME FROM SELFEVALUATION JOIN REL_TEAM__TEAM_MEMBER ON TEAM_MEMBER_ID=EVALUATING_USER_ID JOIN SKILL S ON S.ID=EVALUATED_SKILL_ID JOIN TEAM T ON T.ID = TEAM_ID JOIN JHI_USER U ON U.ID = EVALUATING_USER_ID WHERE TEAM_ID=:teamId and S.CATEGORY=:category"
    )
    List<Selfevaluation> findByTeamAndCategory(@Param("teamId") long teamId, @Param("category") String category);
}
