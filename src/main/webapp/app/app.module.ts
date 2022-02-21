import { LOCALE_ID, NgModule } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/de';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { MissingTranslationHandler, TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgxWebstorageModule, SessionStorageService } from 'ngx-webstorage';
import * as dayjs from 'dayjs';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import './config/dayjs';
import { SharedModule } from 'app/shared/shared.module';
import { AppRoutingModule } from './app-routing.module';
import { EntityRoutingModule } from './entities/entity-routing.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { missingTranslationHandler, translatePartialLoader } from './config/translation.config';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { SktSelfevaluationComponent } from './skt-selfevaluation/skt-selfevaluation.component';
import { SktSelfevaluationRoutingModule } from './skt-selfevaluation/skt-selfevaluation.routing.module';
import { SktRadiobuttonRatingComponent } from './skt-radiobutton-rating/skt-radiobutton-rating.component';
import { SktSelfevaluationAlertComponent } from './skt-selfevaluation-alert/skt-selfevaluation-alert.component';
import { SktOverviewComponent } from './skt-overview/skt-overview.component';
import { SktOverviewRoutingModule } from './skt-overview/skt-overview.routing.module';
import { SktOverviewIndicatorComponent } from './skt-overview-indicator/skt-overview-indicator.component';
import { SktOverviewSidebarComponent } from './skt-overview-sidebar/skt-overview-sidebar.component';
import { SktOverviewTeamDropdownComponent } from './skt-overview-team-dropdown/skt-overview-team-dropdown.component';
import { SktFindExpertComponent } from './skt-find-expert/skt-find-expert.component';
import { sktFindExpertRoutingModule } from './skt-find-expert/skt-find-expert.routing.module';
import { SktFindExpertSkillDropdownComponent } from './skt-find-expert-skill-dropdown/skt-find-expert-skill-dropdown.component';
import { SktOverviewSingleSkillTableComponent } from './skt-overview-single-skill-table/skt-overview-single-skill-table.component';
import { LoginModule } from './login/login.module';

@NgModule({
  imports: [
    BrowserModule,
    SharedModule,
    LoginModule,
    SktSelfevaluationRoutingModule,
    SktOverviewRoutingModule,
    sktFindExpertRoutingModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EntityRoutingModule,
    AppRoutingModule,
    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translatePartialLoader,
        deps: [HttpClient],
      },
      missingTranslationHandler: {
        provide: MissingTranslationHandler,
        useFactory: missingTranslationHandler,
      },
    }),
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'de' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    httpInterceptorProviders,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    SktSelfevaluationComponent,
    SktRadiobuttonRatingComponent,
    SktSelfevaluationAlertComponent,
    SktOverviewComponent,
    SktOverviewIndicatorComponent,
    SktOverviewSidebarComponent,
    SktOverviewTeamDropdownComponent,
    SktFindExpertComponent,
    SktFindExpertSkillDropdownComponent,
    SktOverviewSingleSkillTableComponent,
  ],
  bootstrap: [MainComponent],
})
export class AppModule {
  constructor(
    applicationConfigService: ApplicationConfigService,
    iconLibrary: FaIconLibrary,
    dpConfig: NgbDatepickerConfig,
    translateService: TranslateService,
    sessionStorageService: SessionStorageService
  ) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
    translateService.setDefaultLang('de');
    // if user have changed language and navigates away from the application and back to the application then use previously choosed language
    const langKey = sessionStorageService.retrieve('locale') ?? 'de';
    translateService.use(langKey);
  }
}
