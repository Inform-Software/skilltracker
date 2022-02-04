package de.fhaachen.skilltracker.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import de.fhaachen.skilltracker.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SelfevaluationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelfevaluationDTO.class);
        SelfevaluationDTO selfevaluationDTO1 = new SelfevaluationDTO();
        selfevaluationDTO1.setId(1L);
        SelfevaluationDTO selfevaluationDTO2 = new SelfevaluationDTO();
        assertThat(selfevaluationDTO1).isNotEqualTo(selfevaluationDTO2);
        selfevaluationDTO2.setId(selfevaluationDTO1.getId());
        assertThat(selfevaluationDTO1).isEqualTo(selfevaluationDTO2);
        selfevaluationDTO2.setId(2L);
        assertThat(selfevaluationDTO1).isNotEqualTo(selfevaluationDTO2);
        selfevaluationDTO1.setId(null);
        assertThat(selfevaluationDTO1).isNotEqualTo(selfevaluationDTO2);
    }
}
