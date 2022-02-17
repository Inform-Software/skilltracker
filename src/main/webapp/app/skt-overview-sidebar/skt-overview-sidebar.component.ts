import { Component } from '@angular/core';
import { SkillComponent } from '../entities/skill/list/skill.component';
import { SkillUpdateComponent } from '../entities/skill/update/skill-update.component';

@Component({
  selector: 'jhi-skt-overview-sidebar',
  templateUrl: './skt-overview-sidebar.component.html',
  styleUrls: ['./skt-overview-sidebar.component.scss'],
})
export class SktOverviewSidebarComponent extends SkillUpdateComponent {}
