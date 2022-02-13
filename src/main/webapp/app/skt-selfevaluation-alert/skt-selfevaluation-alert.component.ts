import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Selfevaluation } from '../entities/selfevaluation/selfevaluation.model';
import { AlertComponent } from '../shared/alert/alert.component';

@Component({
  selector: 'jhi-skt-selfevaluation-alert',
  templateUrl: './skt-selfevaluation-alert.component.html',
  styleUrls: ['./skt-selfevaluation-alert.component.scss'],
})
export class SktSelfevaluationAlertComponent implements OnChanges {
  @Input() selfevaluation?: Selfevaluation;
  @Input() clickedRating?: number;
  timer: any;

  ngOnChanges(changes: SimpleChanges): void {
    const alert = document.getElementsByClassName('alert')[0];
    if (this.timer) {
      clearTimeout(this.timer);
      this.timer = null;
      alert.classList.remove('show');
      alert.classList.add('hide');
      setTimeout(function () {
        alert.classList.add('show');
        alert.classList.remove('hide');
      }, 100);
      this.timer = setTimeout(function () {
        alert.classList.remove('show');
        alert.classList.add('hide');
      }, 3000);
    } else {
      alert.classList.add('show');
      alert.classList.remove('hide');
      this.timer = setTimeout(function () {
        alert.classList.remove('show');
        alert.classList.add('hide');
      }, 3000);
    }
  }
}
