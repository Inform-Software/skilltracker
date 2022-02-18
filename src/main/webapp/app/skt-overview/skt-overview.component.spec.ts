import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktOverviewComponent } from './skt-overview.component';

describe('SktOverviewComponent', () => {
  let component: SktOverviewComponent;
  let fixture: ComponentFixture<SktOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktOverviewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
