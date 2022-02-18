import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktFindExpertSkillDropdownComponent } from './skt-find-expert-skill-dropdown.component';

describe('SktFindExpertSkillDropdownComponent', () => {
  let component: SktFindExpertSkillDropdownComponent;
  let fixture: ComponentFixture<SktFindExpertSkillDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktFindExpertSkillDropdownComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktFindExpertSkillDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
