import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SelfevaluationService } from '../service/selfevaluation.service';

import { SelfevaluationComponent } from './selfevaluation.component';

describe('Selfevaluation Management Component', () => {
  let comp: SelfevaluationComponent;
  let fixture: ComponentFixture<SelfevaluationComponent>;
  let service: SelfevaluationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SelfevaluationComponent],
    })
      .overrideTemplate(SelfevaluationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SelfevaluationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SelfevaluationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.selfevaluations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
