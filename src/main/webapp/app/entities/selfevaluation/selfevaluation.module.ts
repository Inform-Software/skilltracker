import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SelfevaluationComponent } from './list/selfevaluation.component';
import { SelfevaluationDetailComponent } from './detail/selfevaluation-detail.component';
import { SelfevaluationUpdateComponent } from './update/selfevaluation-update.component';
import { SelfevaluationDeleteDialogComponent } from './delete/selfevaluation-delete-dialog.component';
import { SelfevaluationRoutingModule } from './route/selfevaluation-routing.module';

@NgModule({
  imports: [SharedModule, SelfevaluationRoutingModule],
  declarations: [
    SelfevaluationComponent,
    SelfevaluationDetailComponent,
    SelfevaluationUpdateComponent,
    SelfevaluationDeleteDialogComponent,
  ],
  entryComponents: [SelfevaluationDeleteDialogComponent],
})
export class SelfevaluationModule {}
