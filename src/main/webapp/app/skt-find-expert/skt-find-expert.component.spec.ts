import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktFindExpertComponent } from './skt-find-expert.component';

describe('SktFindExpertComponent', () => {
  let component: SktFindExpertComponent;
  let fixture: ComponentFixture<SktFindExpertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktFindExpertComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktFindExpertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
