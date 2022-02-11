import { Component, Input } from '@angular/core';
import { SelfevaluationUpdateComponent } from '../entities/selfevaluation/update/selfevaluation-update.component';
import { ISelfevaluation, Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-radiobutton-rating',
  templateUrl: './skt-radiobutton-rating.component.html',
  styleUrls: ['./skt-radiobutton-rating.component.scss'],
})
export class SktRadiobuttonRatingComponent extends SelfevaluationUpdateComponent {
  @Input() selfevaluationID?: number;
  @Input() selfevaluation?: Selfevaluation;

  rating?: number;
  values: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  updateSelfevluation(event: any): void {
    this.rating = event.target.value;
    super.save();
  }

  createFromForm(): ISelfevaluation {
    return {
      ...new Selfevaluation(),
      id: this.selfevaluation?.id,
      value: this.rating,
      evaluatedSkill: this.selfevaluation?.evaluatedSkill,
      evaluatingUser: this.selfevaluation?.evaluatingUser,
    };
  }

  protected onSaveSuccess(): void {
    return;
  }
}
