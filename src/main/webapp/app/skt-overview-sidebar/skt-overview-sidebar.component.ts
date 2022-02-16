import { Component, OnInit } from '@angular/core';
import { SkillComponent } from '../entities/skill/list/skill.component';

@Component({
  selector: 'jhi-skt-overview-sidebar',
  templateUrl: './skt-overview-sidebar.component.html',
  styleUrls: ['./skt-overview-sidebar.component.scss'],
})
export class SktOverviewSidebarComponent extends SkillComponent {}
