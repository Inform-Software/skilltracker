import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'skill',
        data: { pageTitle: 'skilltrackerApp.skill.home.title' },
        loadChildren: () => import('./skill/skill.module').then(m => m.SkillModule),
      },
      {
        path: 'selfevaluation',
        data: { pageTitle: 'skilltrackerApp.selfevaluation.home.title' },
        loadChildren: () => import('./selfevaluation/selfevaluation.module').then(m => m.SelfevaluationModule),
      },
      {
        path: 'team',
        data: { pageTitle: 'skilltrackerApp.team.home.title' },
        loadChildren: () => import('./team/team.module').then(m => m.TeamModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
