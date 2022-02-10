import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISelfevaluation } from '../selfevaluation.model';
import { SelfevaluationService } from '../service/selfevaluation.service';
import { SelfevaluationDeleteDialogComponent } from '../delete/selfevaluation-delete-dialog.component';

@Component({
  selector: 'jhi-selfevaluation',
  templateUrl: './selfevaluation.component.html',
})
export class SelfevaluationComponent implements OnInit {
  selfevaluations?: ISelfevaluation[];
  isLoading = false;

  constructor(protected selfevaluationService: SelfevaluationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.selfevaluationService.query().subscribe(
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
    this.loadAll();
  }

  trackId(index: number, item: ISelfevaluation): number {
    return item.id!;
  }

  delete(selfevaluation: ISelfevaluation): void {
    const modalRef = this.modalService.open(SelfevaluationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.selfevaluation = selfevaluation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
