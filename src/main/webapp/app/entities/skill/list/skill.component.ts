import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISkill } from '../skill.model';
import { SkillService } from '../service/skill.service';
import { SkillDeleteDialogComponent } from '../delete/skill-delete-dialog.component';

@Component({
  selector: 'jhi-skill',
  templateUrl: './skill.component.html',
})
export class SkillComponent implements OnInit {
  skills?: ISkill[];
  isLoading = false;

  constructor(protected skillService: SkillService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.skillService.query().subscribe(
      (res: HttpResponse<ISkill[]>) => {
        this.isLoading = false;
        this.skills = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISkill): number {
    return item.id!;
  }

  delete(skill: ISkill): void {
    const modalRef = this.modalService.open(SkillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.skill = skill;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
