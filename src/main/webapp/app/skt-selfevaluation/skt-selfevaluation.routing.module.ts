import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SktSelfevaluationComponent } from './skt-selfevaluation.component';
import { SelfevaluationComponent } from '../entities/selfevaluation/list/selfevaluation.component';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'skt-selfevaluation',
        data: {},
        component: SktSelfevaluationComponent,
      },
    ]),
  ],
})
export class SktSelfevaluationRoutingModule {}
