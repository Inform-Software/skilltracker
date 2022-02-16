import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SktOverviewComponent } from './skt-overview.component';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'skt-overview',
        data: {},
        component: SktOverviewComponent,
      },
    ]),
  ],
})
export class SktOverviewRoutingModule {}
