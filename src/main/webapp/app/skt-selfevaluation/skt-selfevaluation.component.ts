import { Component } from '@angular/core';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';
import { HttpResponse } from '@angular/common/http';
import { ISelfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-selfevaluation',
  templateUrl: './skt-selfevaluation.component.html',
  styleUrls: ['./skt-selfevaluation.component.scss'],
})
export class SktSelfevaluationComponent extends SelfevaluationComponent {
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
}
