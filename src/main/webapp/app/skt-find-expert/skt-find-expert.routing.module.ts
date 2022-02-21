import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SktFindExpertComponent } from './skt-find-expert.component';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'skt-find-expert',
        data: {},
        component: SktFindExpertComponent,
      },
    ]),
  ],
})
export class sktFindExpertRoutingModule {}
