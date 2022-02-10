package de.fhaachen.skilltracker.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SelfevaluationMapperTest {

    private SelfevaluationMapper selfevaluationMapper;

    @BeforeEach
    public void setUp() {
        selfevaluationMapper = new SelfevaluationMapperImpl();
    }
}
