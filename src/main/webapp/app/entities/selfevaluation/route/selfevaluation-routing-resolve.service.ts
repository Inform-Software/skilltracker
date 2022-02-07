import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';
import { SelfevaluationService } from '../service/selfevaluation.service';

@Injectable({ providedIn: 'root' })
export class SelfevaluationRoutingResolveService implements Resolve<ISelfevaluation> {
  constructor(protected service: SelfevaluationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISelfevaluation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((selfevaluation: HttpResponse<Selfevaluation>) => {
          if (selfevaluation.body) {
            return of(selfevaluation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Selfevaluation());
  }
}
