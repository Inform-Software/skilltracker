import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { ITeam, Team } from '../../entities/team/team.model';
import { TeamComponent } from '../../entities/team/list/team.component';
import { TeamService } from '../../entities/team/service/team.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent extends TeamComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  team?: ITeam;
  currentTeam?: ITeam;
  changedTeam?: ITeam;
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined],
  });
  selectedTeam?: ITeam;

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private translateService: TranslateService,
    protected teamService: TeamService,
    protected modalService: NgbModal
  ) {
    super(teamService, modalService);
  }

  loadTeam(): void {
    this.isLoading = true;

    this.teamService.findByUser(this.account.login).subscribe(
      (res: HttpResponse<ITeam[]>) => {
        this.isLoading = false;
        const teamHelper = res.body ?? [];
        if (teamHelper.length > 0) {
          this.currentTeam = teamHelper.pop();
        }
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    super.ngOnInit();
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email,
          langKey: account.langKey,
        });
        this.account = account;
      }
    });
    this.loadTeam();
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;
    this.team = this.selectedTeam;

    if (this.currentTeam !== undefined) {
      this.currentTeam.teamMembers = this.currentTeam.teamMembers?.filter(team => team.login !== this.account.login);
      this.teamService.update(this.currentTeam).subscribe(() => {
        this.success = true;
      });
    }

    this.team?.teamMembers?.push(this.account);
    this.teamService.update(this.team!).subscribe(() => {
      this.success = true;
    });

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.translateService.currentLang) {
        this.translateService.use(this.account.langKey);
      }
    });
  }

  changer(event: any): void {
    this.selectedTeam = event;
    const test = this.selectedTeam?.name;
  }
}
