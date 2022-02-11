import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktRadiobuttonRatingComponent } from './skt-radiobutton-rating.component';

describe('SktRadiobuttonRatingComponent', () => {
  let component: SktRadiobuttonRatingComponent;
  let fixture: ComponentFixture<SktRadiobuttonRatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktRadiobuttonRatingComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktRadiobuttonRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
