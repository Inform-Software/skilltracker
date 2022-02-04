import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SelfevaluationDetailComponent } from './selfevaluation-detail.component';

describe('Selfevaluation Management Detail Component', () => {
  let comp: SelfevaluationDetailComponent;
  let fixture: ComponentFixture<SelfevaluationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelfevaluationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ selfevaluation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SelfevaluationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SelfevaluationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load selfevaluation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.selfevaluation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
