import { Component } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation, Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';
import { Observable } from 'rxjs';
import { EntityResponseType } from '../entities/selfevaluation/service/selfevaluation.service';

@Component({
  selector: 'jhi-skt-overview',
  templateUrl: './skt-overview.component.html',
  styleUrls: ['./skt-overview.component.scss'],
})
export class SktOverviewComponent extends SelfevaluationComponent {
  clickedSelfevaluation?: number;
  clickedRating?: number;

  loadAllByCurrentUser(): void {
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

  displayAlert(selfevaluation: Selfevaluation): boolean {
    return selfevaluation.id === this.clickedSelfevaluation;
  }

  ngOnInit(): void {
    this.loadAllByCurrentUser();
  }
}
