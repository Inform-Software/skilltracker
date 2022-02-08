import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BasedataComponent } from './basedata.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'basedata',
        component: BasedataComponent,
      },
    ]),
  ],
})
export class BasedataRoutingModule {}
