import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISelfevaluation } from '../selfevaluation.model';
import { SelfevaluationService } from '../service/selfevaluation.service';

@Component({
  templateUrl: './selfevaluation-delete-dialog.component.html',
})
export class SelfevaluationDeleteDialogComponent {
  selfevaluation?: ISelfevaluation;

  constructor(protected selfevaluationService: SelfevaluationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.selfevaluationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
