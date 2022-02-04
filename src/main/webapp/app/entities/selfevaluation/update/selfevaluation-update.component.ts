import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISelfevaluation, Selfevaluation } from '../selfevaluation.model';
import { SelfevaluationService } from '../service/selfevaluation.service';
import { ISkill } from 'app/entities/skill/skill.model';
import { SkillService } from 'app/entities/skill/service/skill.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-selfevaluation-update',
  templateUrl: './selfevaluation-update.component.html',
})
export class SelfevaluationUpdateComponent implements OnInit {
  isSaving = false;

  skillsSharedCollection: ISkill[] = [];
  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required, Validators.min(0), Validators.max(10)]],
    evaluated_skill: [null, Validators.required],
    evaluating_user: [null, Validators.required],
  });

  constructor(
    protected selfevaluationService: SelfevaluationService,
    protected skillService: SkillService,
    protected userService: UserService,
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

  trackSkillById(index: number, item: ISkill): number {
    return item.id!;
  }

  trackUserById(index: number, item: IUser): number {
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
      evaluated_skill: selfevaluation.evaluated_skill,
      evaluating_user: selfevaluation.evaluating_user,
    });

    this.skillsSharedCollection = this.skillService.addSkillToCollectionIfMissing(
      this.skillsSharedCollection,
      selfevaluation.evaluated_skill
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, selfevaluation.evaluating_user);
  }

  protected loadRelationshipsOptions(): void {
    this.skillService
      .query()
      .pipe(map((res: HttpResponse<ISkill[]>) => res.body ?? []))
      .pipe(map((skills: ISkill[]) => this.skillService.addSkillToCollectionIfMissing(skills, this.editForm.get('evaluated_skill')!.value)))
      .subscribe((skills: ISkill[]) => (this.skillsSharedCollection = skills));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('evaluating_user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): ISelfevaluation {
    return {
      ...new Selfevaluation(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      evaluated_skill: this.editForm.get(['evaluated_skill'])!.value,
      evaluating_user: this.editForm.get(['evaluating_user'])!.value,
    };
  }
}
