import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktSelfevaluationComponent } from './skt-selfevaluation.component';

describe('SktSelfevaluationComponent', () => {
  let component: SktSelfevaluationComponent;
  let fixture: ComponentFixture<SktSelfevaluationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktSelfevaluationComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktSelfevaluationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
