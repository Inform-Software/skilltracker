import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  displaySource = new BehaviorSubject<boolean>(false);
  display = this.displaySource.asObservable();

  setDisplay(display: boolean): void {
    this.displaySource.next(display);
  }
}
