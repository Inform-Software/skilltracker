import { Component, Input, OnInit } from '@angular/core';
import { Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';

@Component({
  selector: 'jhi-skt-selfevaluation-alert',
  templateUrl: './skt-selfevaluation-alert.component.html',
  styleUrls: ['./skt-selfevaluation-alert.component.scss'],
})
export class SktSelfevaluationAlertComponent {
  @Input() selfevaluation?: Selfevaluation;
  @Input() clickedRating?: number;
}
