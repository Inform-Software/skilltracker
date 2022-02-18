import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktOverviewSidebarComponent } from './skt-overview-sidebar.component';

describe('SktOverviewSidebarComponent', () => {
  let component: SktOverviewSidebarComponent;
  let fixture: ComponentFixture<SktOverviewSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktOverviewSidebarComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktOverviewSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
