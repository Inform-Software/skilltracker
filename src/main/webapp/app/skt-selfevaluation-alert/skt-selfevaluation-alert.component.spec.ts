import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktSelfevaluationAlertComponent } from './skt-selfevaluation-alert.component';

describe('SktSelfevaluationAlertComponent', () => {
  let component: SktSelfevaluationAlertComponent;
  let fixture: ComponentFixture<SktSelfevaluationAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktSelfevaluationAlertComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktSelfevaluationAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
