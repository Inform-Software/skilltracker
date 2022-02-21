import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktOverviewSingleSkillTableComponent } from './skt-overview-single-skill-table.component';

describe('SktOverviewSingleSkillTableComponent', () => {
  let component: SktOverviewSingleSkillTableComponent;
  let fixture: ComponentFixture<SktOverviewSingleSkillTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktOverviewSingleSkillTableComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktOverviewSingleSkillTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
