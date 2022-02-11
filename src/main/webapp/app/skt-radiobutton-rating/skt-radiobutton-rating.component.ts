import { Component, Input } from '@angular/core';

@Component({
  selector: 'jhi-skt-radiobutton-rating',
  templateUrl: './skt-radiobutton-rating.component.html',
  styleUrls: ['./skt-radiobutton-rating.component.scss'],
})
export class SktRadiobuttonRatingComponent {
  @Input() selfevaluationID?: number;

  rating?: number;
  values: any = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  radioChangeHandler(event: any): void {
    this.rating = event.target.value;
  }
}
