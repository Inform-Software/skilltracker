import { Component, EventEmitter, Output } from '@angular/core';
import { TeamComponent } from '../entities/team/list/team.component';

@Component({
  selector: 'jhi-skt-overview-team-dropdown',
  templateUrl: './skt-overview-team-dropdown.component.html',
  styleUrls: ['./skt-overview-team-dropdown.component.scss'],
})
export class SktOverviewTeamDropdownComponent extends TeamComponent {
  @Output() teamId: EventEmitter<number> = new EventEmitter<number>();
  values: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  emitTeam(event: any): void {
    this.teamId.emit(event.target.value);
  }
}