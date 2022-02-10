import { IUser } from 'app/entities/user/user.model';

export interface ITeam {
  id?: number;
  name?: string;
  teamOfUsers?: IUser[] | null;
}

export class Team implements ITeam {
  constructor(public id?: number, public name?: string, public teamOfUsers?: IUser[] | null) {}
}

export function getTeamIdentifier(team: ITeam): number | undefined {
  return team.id;
}
