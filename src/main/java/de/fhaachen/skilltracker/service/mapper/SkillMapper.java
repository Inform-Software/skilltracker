package de.fhaachen.skilltracker.service.mapper;

import de.fhaachen.skilltracker.domain.Skill;
import de.fhaachen.skilltracker.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SkillDTO toDtoName(Skill skill);
}
