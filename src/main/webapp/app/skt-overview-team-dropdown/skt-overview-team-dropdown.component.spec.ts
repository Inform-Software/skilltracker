import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktOverviewTeamDropdownComponent } from './skt-overview-team-dropdown.component';

describe('SktOverviewTeamDropdownComponent', () => {
  let component: SktOverviewTeamDropdownComponent;
  let fixture: ComponentFixture<SktOverviewTeamDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktOverviewTeamDropdownComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktOverviewTeamDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
