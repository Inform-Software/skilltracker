import { Component, OnDestroy, OnInit } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation } from '../entities/selfevaluation/selfevaluation.model';
import { DataService } from '../Service/data.service';
import { SelfevaluationService } from '../entities/selfevaluation/service/selfevaluation.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SkillService } from '../entities/skill/service/skill.service';
import { ISkill } from '../entities/skill/skill.model';

@Component({
  selector: 'jhi-skt-overview',
  templateUrl: './skt-overview.component.html',
  styleUrls: ['./skt-overview.component.scss'],
})
export class SktOverviewComponent extends SelfevaluationComponent implements OnInit, OnDestroy {
  teamId?: number;
  category?: string;
  skills?: ISkill[];

  constructor(
    protected selfevaluationService: SelfevaluationService,
    protected modalService: NgbModal,
    private data: DataService,
    private skillService: SkillService
  ) {
    super(selfevaluationService, modalService);
  }

  loadAllByTeamAndCategory(): void {
    this.isLoading = true;

    this.selfevaluationService.findByTeamAndCategory(this.teamId!, this.category!).subscribe(
      (res: HttpResponse<ISelfevaluation[]>) => {
        this.isLoading = false;
        this.selfevaluations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  loadSkillsByCategory(): void {
    this.isLoading = true;

    this.skillService.findSkillsByCategory(this.category!).subscribe(
      (res: HttpResponse<ISkill[]>) => {
        this.isLoading = false;
        this.skills = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAllByTeamAndCategory();
    this.data.setDisplay(true);
    this.loadSkillsByCategory();
  }

  ngOnDestroy(): void {
    this.data.setDisplay(false);
  }

  getTeamId($event: number): void {
    this.teamId = $event;
    this.loadAllByTeamAndCategory();
  }

  getCategory($event: string): void {
    this.category = $event;
    this.loadAllByTeamAndCategory();
  }
}
