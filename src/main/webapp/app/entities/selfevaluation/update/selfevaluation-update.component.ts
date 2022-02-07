import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';
import { SelfevaluationService } from '../service/selfevaluation.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';

@Component({
  selector: 'jhi-selfevaluation-update',
  templateUrl: './selfevaluation-update.component.html',
})
export class SelfevaluationUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  skillsSharedCollection: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required, Validators.min(0), Validators.max(10)]],
    evaluatinguser: [null, Validators.required],
    evaluatedskill: [null, Validators.required],
  });

  constructor(
    protected selfevaluationService: SelfevaluationService,
    protected userService: UserService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ selfevaluation }) => {
      this.updateForm(selfevaluation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const selfevaluation = this.createFromForm();
    if (selfevaluation.id !== undefined) {
      this.subscribeToSaveResponse(this.selfevaluationService.update(selfevaluation));
    } else {
      this.subscribeToSaveResponse(this.selfevaluationService.create(selfevaluation));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISelfevaluation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(selfevaluation: ISelfevaluation): void {
    this.editForm.patchValue({
      id: selfevaluation.id,
      value: selfevaluation.value,
      evaluatinguser: selfevaluation.evaluatinguser,
      evaluatedskill: selfevaluation.evaluatedskill,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, selfevaluation.evaluatinguser);
    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(
      this.skillsSharedCollection,
      selfevaluation.evaluatedskill
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('evaluatinguser')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.skillService
      .query()
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, this.editForm.get('evaluatedskill')!.value)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));
  }

  protected createFromForm(): ISelfevaluation {
    return {
      ...new Selfevaluation(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      evaluatinguser: this.editForm.get(['evaluatinguser'])!.value,
      evaluatedskill: this.editForm.get(['evaluatedskill'])!.value,
    };
  }
}
