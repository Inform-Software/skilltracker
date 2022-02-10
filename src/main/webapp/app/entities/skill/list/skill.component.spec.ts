import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SkillService } from '../service/skill.service';

import { SkillComponent } from './skill.component';

describe('Skill Management Component', () => {
  let comp: SkillComponent;
  let fixture: ComponentFixture<SkillComponent>;
  let service: SkillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SkillComponent],
    })
      .overrideTemplate(SkillComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SkillComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SkillService);

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
    expect(comp.skills?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
