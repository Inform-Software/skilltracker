package de.fhaachen.skilltracker.service.mapper;

import de.fhaachen.skilltracker.domain.Selfevaluation;
import de.fhaachen.skilltracker.service.dto.SelfevaluationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Selfevaluation} and its DTO {@link SelfevaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, SkillMapper.class })
public interface SelfevaluationMapper extends EntityMapper<SelfevaluationDTO, Selfevaluation> {
    @Mapping(target = "evaluatinguser", source = "evaluatinguser", qualifiedByName = "login")
    @Mapping(target = "evaluatedskill", source = "evaluatedskill", qualifiedByName = "name")
    SelfevaluationDTO toDto(Selfevaluation s);
}
