package de.fhaachen.skilltracker.service.mapper;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.service.dto.SelfevaluationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Selfevaluation} and its DTO {@link SelfevaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class, UserMapper.class })
public interface SelfevaluationMapper extends EntityMapper<SelfevaluationDTO, Selfevaluation> {
    @Mapping(target = "evaluated_skill", source = "evaluated_skill", qualifiedByName = "name")
    @Mapping(target = "evaluating_user", source = "evaluating_user", qualifiedByName = "login")
    SelfevaluationDTO toDto(Selfevaluation s);
}
