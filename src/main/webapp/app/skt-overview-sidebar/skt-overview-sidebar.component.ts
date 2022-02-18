import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SkillUpdateComponent } from '../entities/skill/update/skill-update.component';

@Component({
  selector: 'jhi-skt-overview-sidebar',
  templateUrl: './skt-overview-sidebar.component.html',
  styleUrls: ['./skt-overview-sidebar.component.scss'],
})
export class SktOverviewSidebarComponent extends SkillUpdateComponent implements OnInit {
  clickedCategory?: string;
  @Output() category: EventEmitter<string> = new EventEmitter<string>();

  emitCategory(category: string): void {
    this.clickedCategory = category;
    this.category.emit(category);
  }

  isClicked(category: string): boolean {
    return category === this.clickedCategory;
  }

  ngOnInit(): void {
    this.clickedCategory = this.skillCategoryValues[0];
    this.category.emit(this.clickedCategory);
  }
}
