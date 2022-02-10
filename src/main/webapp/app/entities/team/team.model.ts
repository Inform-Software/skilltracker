import { IUser } from 'app/entities/user/user.model';

export interface ITeam {
  id?: number;
  name?: string;
  teamMembers?: IUser[] | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public name?: string, public teamMembers?: IUser[] | null) {}
}

export function getTeamIdentifier(team: ITeam): number | undefined {
  return team.id;
}
