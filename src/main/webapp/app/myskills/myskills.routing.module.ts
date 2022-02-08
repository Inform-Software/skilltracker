import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MyskillsComponent } from './myskills.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'myskills',
        component: MyskillsComponent,
      },
    ]),
  ],
})
export class MyskillsRoutingModule {}
