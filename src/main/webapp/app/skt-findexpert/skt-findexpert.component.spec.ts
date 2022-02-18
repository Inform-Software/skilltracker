import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SktFindexpertComponent } from './skt-findexpert.component';

describe('SktFindexpertComponent', () => {
  let component: SktFindexpertComponent;
  let fixture: ComponentFixture<SktFindexpertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SktFindexpertComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SktFindexpertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
