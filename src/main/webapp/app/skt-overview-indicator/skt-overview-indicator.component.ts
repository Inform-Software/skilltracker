import { Component, Input } from '@angular/core';
import { Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-overview-indicator',
  templateUrl: './skt-overview-indicator.component.html',
  styleUrls: ['./skt-overview-indicator.component.scss'],
})
export class SktOverviewIndicatorComponent {
  @Input() selfevaluation?: Selfevaluation;
  values: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  isChecked(value: number): boolean {
    const test = value === this.selfevaluation?.value;
    return test;
  }
}
