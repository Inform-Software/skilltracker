import { Component, OnInit } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation, Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-find-expert',
  templateUrl: './skt-find-expert.component.html',
  styleUrls: ['./skt-find-expert.component.scss'],
})
export class SktFindExpertComponent extends SelfevaluationComponent implements OnInit {
  skillId?: number;

  loadAllBySkill(): void {
    this.isLoading = true;

    this.selfevaluationService.findBySkillDesc(this.skillId!).subscribe(
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
    this.loadAllBySkill();
  }

  getSkillId($event: number): void {
    this.skillId = $event;
    this.loadAllBySkill();
  }
}
