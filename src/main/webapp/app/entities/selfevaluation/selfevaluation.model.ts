import { ISkill } from 'app/entities/skill/skill.model';
import { IUser } from 'app/entities/user/user.model';

export interface ISelfevaluation {
  id?: number;
  value?: number;
  wantToImprove?: boolean | null;
  isEvaluated?: boolean | null;
  evaluatedSkill?: ISkill;
  evaluatingUser?: IUser;
}

export class Selfevaluation implements ISelfevaluation {
  constructor(
    public id?: number,
    public value?: number,
    public wantToImprove?: boolean | null,
    public isEvaluated?: boolean | null,
    public evaluatedSkill?: ISkill,
    public evaluatingUser?: IUser
  ) {
    this.wantToImprove = this.wantToImprove ?? false;
    this.isEvaluated = this.isEvaluated ?? false;
  }
}

export function getSelfevaluationIdentifier(selfevaluation: ISelfevaluation): number | undefined {
  return selfevaluation.id;
}
