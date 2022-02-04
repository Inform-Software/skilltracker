jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SelfevaluationService } from '../service/selfevaluation.service';
import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { SelfevaluationUpdateComponent } from './selfevaluation-update.component';

describe('Selfevaluation Management Update Component', () => {
  let comp: SelfevaluationUpdateComponent;
  let fixture: ComponentFixture<SelfevaluationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let selfevaluationService: SelfevaluationService;
  let skillService: SkillService;
  let userService: UserService;

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
    skillService = TestBed.inject(SkillService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Skill query and add missing value', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluated_skill: ISkill = { id: 35730 };
      selfevaluation.evaluated_skill = evaluated_skill;

      const skillCollection: ISkill[] = [{ id: 79346 }];
      jest.spyOn(skillService, 'query').mockReturnValue(of(new HttpResponse({ body: skillCollection })));
      const additionalSkills = [evaluated_skill];
      const expectedCollection: ISkill[] = [...additionalSkills, ...skillCollection];
      jest.spyOn(skillService, 'addSkillToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(skillService.query).toHaveBeenCalled();
      expect(skillService.addSkillToCollectionIfMissing).toHaveBeenCalledWith(skillCollection, ...additionalSkills);
      expect(comp.skillsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluating_user: IUser = { id: 261 };
      selfevaluation.evaluating_user = evaluating_user;

      const userCollection: IUser[] = [{ id: 50005 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [evaluating_user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const selfevaluation: ISelfevaluation = { id: 456 };
      const evaluated_skill: ISkill = { id: 85460 };
      selfevaluation.evaluated_skill = evaluated_skill;
      const evaluating_user: IUser = { id: 13316 };
      selfevaluation.evaluating_user = evaluating_user;

      activatedRoute.data = of({ selfevaluation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(selfevaluation));
      expect(comp.skillsSharedCollection).toContain(evaluated_skill);
      expect(comp.usersSharedCollection).toContain(evaluating_user);
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
    describe('trackSkillById', () => {
      it('Should return tracked Skill primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSkillById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
