import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation, Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';

@Component({
  selector: 'jhi-skt-overview-single-skill-table',
  templateUrl: './skt-overview-single-skill-table.component.html',
  styleUrls: ['./skt-overview-single-skill-table.component.scss'],
})
export class SktOverviewSingleSkillTableComponent extends SelfevaluationComponent implements OnInit, OnChanges {
  @Input() skillId?: number;
  @Input() teamId?: number;
  @Input() skillName?: string;

  loadAllByTeamAndSkill(): void {
    this.isLoading = true;

    this.selfevaluationService.findByTeamAndSkill(this.teamId!, this.skillId!).subscribe(
      (res: HttpResponse<ISelfevaluation[]>) => {
        this.isLoading = false;
        this.selfevaluations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAllByTeamAndSkill();
  }

  ngOnChanges(): void {
    this.loadAllByTeamAndSkill();
  }
}
