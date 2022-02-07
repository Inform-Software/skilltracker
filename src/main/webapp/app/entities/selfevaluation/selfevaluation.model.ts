import { IUser } from 'app/entities/user/user.model';
import { ISkill } from 'app/entities/skill/skill.model';

export interface ISelfevaluation {
  id?: number;
  value?: number;
  evaluatinguser?: IUser;
  evaluatedskill?: ISkill;
}

export class Selfevaluation implements ISelfevaluation {
  constructor(public id?: number, public value?: number, public evaluatinguser?: IUser, public evaluatedskill?: ISkill) {}
}

export function getSelfevaluationIdentifier(selfevaluation: ISelfevaluation): number | undefined {
  return selfevaluation.id;
}
