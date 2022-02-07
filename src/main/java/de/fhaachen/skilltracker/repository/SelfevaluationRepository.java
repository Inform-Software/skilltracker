package de.fhaachen.skilltracker.repository;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Selfevaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelfevaluationRepository extends JpaRepository<Selfevaluation, Long> {
    @Query("select selfevaluation from Selfevaluation selfevaluation where selfevaluation.evaluatinguser.login = ?#{principal.username}")
    List<Selfevaluation> findByEvaluatinguserIsCurrentUser();
}
