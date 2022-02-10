import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SelfevaluationComponent } from '../list/selfevaluation.component';
import { SelfevaluationDetailComponent } from '../detail/selfevaluation-detail.component';
import { SelfevaluationUpdateComponent } from '../update/selfevaluation-update.component';
import { SelfevaluationRoutingResolveService } from './selfevaluation-routing-resolve.service';

const selfevaluationRoute: Routes = [
  {
    path: '',
    component: SelfevaluationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SelfevaluationDetailComponent,
    resolve: {
      selfevaluation: SelfevaluationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SelfevaluationUpdateComponent,
    resolve: {
      selfevaluation: SelfevaluationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SelfevaluationUpdateComponent,
    resolve: {
      selfevaluation: SelfevaluationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(selfevaluationRoute)],
  exports: [RouterModule],
})
export class SelfevaluationRoutingModule {}
