import { SkillCategory } from 'app/entities/enumerations/skill-category.model';

export interface ISkill {
  id?: number;
  name?: string;
  category?: SkillCategory;
}

export class Skill implements ISkill {
  constructor(public id?: number, public name?: string, public category?: SkillCategory) {}
}

export function getSkillIdentifier(skill: ISkill): number | undefined {
  return skill.id;
}
