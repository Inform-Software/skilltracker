import { ISkill } from 'app/entities/skill/skill.model';
import { IUser } from 'app/entities/user/user.model';

export interface ISelfevaluation {
  id?: number;
  value?: number;
  evaluated_skill?: ISkill;
  evaluating_user?: IUser;
}

export class Selfevaluation implements ISelfevaluation {
  constructor(public id?: number, public value?: number, public evaluated_skill?: ISkill, public evaluating_user?: IUser) {}
}

export function getSelfevaluationIdentifier(selfevaluation: ISelfevaluation): number | undefined {
  return selfevaluation.id;
}
