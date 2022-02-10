import { ISkill } from 'app/entities/skill/skill.model';
import { IUser } from 'app/entities/user/user.model';

export interface ISelfevaluation {
  id?: number;
  value?: number;
  evaluatedSkill?: ISkill;
  evaluatingUser?: IUser;
}

export class Selfevaluation implements ISelfevaluation {
  constructor(public id?: number, public value?: number, public evaluatedSkill?: ISkill, public evaluatingUser?: IUser) {}
}

export function getSelfevaluationIdentifier(selfevaluation: ISelfevaluation): number | undefined {
  return selfevaluation.id;
}
