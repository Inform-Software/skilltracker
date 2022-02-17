import { Component, OnInit } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation, Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-selfevaluation',
  templateUrl: './skt-selfevaluation.component.html',
  styleUrls: ['./skt-selfevaluation.component.scss'],
})
export class SktSelfevaluationComponent extends SelfevaluationComponent implements OnInit {
  clickedSelfevaluation?: number;
  clickedRating?: number;

  loadAllByCurrentUser(): void {
    this.isLoading = true;

    this.selfevaluationService.sktquery().subscribe(
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
    this.loadAllByCurrentUser();
  }

  displayAlert(selfevaluation: Selfevaluation): boolean {
    return selfevaluation.id === this.clickedSelfevaluation;
  }

  getSelfevaluationID($event: number): void {
    this.clickedSelfevaluation = $event;
  }

  getClickedRating($event: number): void {
    this.clickedRating = $event;
  }
}
