import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeam } from '../team.model';
import { TeamService } from '../service/team.service';
import { TeamDeleteDialogComponent } from '../delete/team-delete-dialog.component';

@Component({
  selector: 'jhi-team',
  templateUrl: './team.component.html',
})
export class TeamComponent implements OnInit {
  teams?: ITeam[];
  isLoading = false;

  constructor(protected teamService: TeamService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.teamService.query().subscribe(
      (res: HttpResponse<ITeam[]>) => {
        this.isLoading = false;
        this.teams = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITeam): number {
    return item.id!;
  }

  delete(team: ITeam): void {
    const modalRef = this.modalService.open(TeamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.team = team;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
