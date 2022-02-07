jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SelfevaluationService } from '../service/selfevaluation.service';
import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

import { SelfevaluationUpdateComponent } from './selfevaluation-update.component';

describe('Selfevaluation Management Update Component', () => {
  let comp: SelfevaluationUpdateComponent;
  let fixture: ComponentFixture<SelfevaluationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let selfevaluationService: SelfevaluationService;
  let userService: UserService;
  let skillService: SkillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SelfevaluationUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SelfevaluationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SelfevaluationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    selfevaluationService = TestBed.inject(SelfevaluationService);
    userService = TestBed.inject(UserService);
    skillService = TestBed.inject(SkillService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluatinguser: IUser = { id: 261 };
      selfevaluation.evaluatinguser = evaluatinguser;

      const userCollection: IUser[] = [{ id: 50005 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [evaluatinguser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Skill query and add missing value', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluatedskill: ISkill = { id: 35730 };
      selfevaluation.evaluatedskill = evaluatedskill;

      const skillCollection: ISkill[] = [{ id: 79346 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [evaluatedskill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluatinguser: IUser = { id: 13316 };
      selfevaluation.evaluatinguser = evaluatinguser;
      const evaluatedskill: ISkill = { id: 85460 };
      selfevaluation.evaluatedskill = evaluatedskill;

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(selfevaluation));
      expect(comp.usersSharedCollection).toContain(evaluatinguser);
      expect(comp.skillsSharedCollection).toContain(evaluatedskill);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Selfevaluation>>();
      const selfevaluation = { id: 123 };
      jest.spyOn(selfevaluationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: selfevaluation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(selfevaluationService.update).toHaveBeenCalledWith(selfevaluation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Selfevaluation>>();
      const selfevaluation = new Selfevaluation();
      jest.spyOn(selfevaluationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: selfevaluation }));
      saveSubject.complete();

      // THEN
      expect(selfevaluationService.create).toHaveBeenCalledWith(selfevaluation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Selfevaluation>>();
      const selfevaluation = { id: 123 };
      jest.spyOn(selfevaluationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(selfevaluationService.update).toHaveBeenCalledWith(selfevaluation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSkillById', () => {
      it('Should return tracked Skill primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSkillById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
