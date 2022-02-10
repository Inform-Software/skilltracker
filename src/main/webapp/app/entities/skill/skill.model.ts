import { SkillCategory } from 'app/entities/enumerations/skill-category.model';

export interface ISkill {
  id?: number;
  category?: SkillCategory;
  name?: string;
}

export class Skill implements ISkill {
  constructor(public id?: number, public category?: SkillCategory, public name?: string) {}
}

export function getSkillIdentifier(skill: ISkill): number | undefined {
  return skill.id;
}
