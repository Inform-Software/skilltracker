import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktOverviewIndicatorComponent } from './skt-overview-indicator.component';

describe('SktOverviewIndicatorComponent', () => {
  let component: SktOverviewIndicatorComponent;
  let fixture: ComponentFixture<SktOverviewIndicatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktOverviewIndicatorComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktOverviewIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
