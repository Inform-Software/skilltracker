import { Component, EventEmitter, Output } from '@angular/core';
import { TeamComponent } from '../entities/team/list/team.component';

@Component({
  selector: 'jhi-skt-overview-team-dropdown',
  templateUrl: './skt-overview-team-dropdown.component.html',
  styleUrls: ['./skt-overview-team-dropdown.component.scss'],
})
export class SktOverviewTeamDropdownComponent extends TeamComponent {
  @Output() teamId: EventEmitter<number> = new EventEmitter<number>();

  emitTeam(event: any): void {
    this.teamId.emit(event.target.value);
  }
}
