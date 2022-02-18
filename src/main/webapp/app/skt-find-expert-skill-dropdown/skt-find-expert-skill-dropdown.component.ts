import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SkillComponent } from '../entities/skill/list/skill.component';

@Component({
  selector: 'jhi-skt-find-expert-skill-dropdown',
  templateUrl: './skt-find-expert-skill-dropdown.component.html',
  styleUrls: ['./skt-find-expert-skill-dropdown.component.scss'],
})
export class SktFindExpertSkillDropdownComponent extends SkillComponent {
  @Output() skillId: EventEmitter<number> = new EventEmitter<number>();
  values: number[] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  emitSkill(event: any): void {
    this.skillId.emit(event.target.value);
  }
}
