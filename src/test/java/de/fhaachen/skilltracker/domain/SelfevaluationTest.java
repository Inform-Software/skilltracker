package de.fhaachen.skilltracker.domain;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhaachen.skilltracker.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SelfevaluationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Selfevaluation.class);
        Selfevaluation selfevaluation1 = new Selfevaluation();
        selfevaluation1.setId(1L);
        Selfevaluation selfevaluation2 = new Selfevaluation();
        selfevaluation2.setId(selfevaluation1.getId());
        assertThat(selfevaluation1).isEqualTo(selfevaluation2);
        selfevaluation2.setId(2L);
        assertThat(selfevaluation1).isNotEqualTo(selfevaluation2);
        selfevaluation1.setId(null);
        assertThat(selfevaluation1).isNotEqualTo(selfevaluation2);
    }
}
