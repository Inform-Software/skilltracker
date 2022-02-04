import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISelfevaluation } from '../selfevaluation.model';

@Component({
  selector: 'jhi-selfevaluation-detail',
  templateUrl: './selfevaluation-detail.component.html',
})
export class SelfevaluationDetailComponent implements OnInit {
  selfevaluation: ISelfevaluation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ selfevaluation }) => {
      this.selfevaluation = selfevaluation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
