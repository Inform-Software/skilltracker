import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SktSelfevaluationComponent } from './skt-selfevaluation.component';

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
