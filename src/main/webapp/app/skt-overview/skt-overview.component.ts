import { Component, OnDestroy, OnInit } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation } from '../entities/selfevaluation/selfevaluation.model';
import { DataService } from '../Service/data.service';
import { SelfevaluationService } from '../entities/selfevaluation/service/selfevaluation.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-skt-overview',
  templateUrl: './skt-overview.component.html',
  styleUrls: ['./skt-overview.component.scss'],
})
export class SktOverviewComponent extends SelfevaluationComponent implements OnInit, OnDestroy {
  constructor(protected selfevaluationService: SelfevaluationService, protected modalService: NgbModal, private data: DataService) {
    super(selfevaluationService, modalService);
  }

  loadAllByTeamAndCategory(): void {
    this.isLoading = true;

    this.selfevaluationService.findByTeamAndCategory(2, 'VDS_V10').subscribe(
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
    this.loadAllByTeamAndCategory();
    this.data.setDisplay(true);
  }

  ngOnDestroy(): void {
    this.data.setDisplay(false);
  }
}
