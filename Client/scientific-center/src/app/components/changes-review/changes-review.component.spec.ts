import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangesReviewComponent } from './changes-review.component';

describe('ChangesReviewComponent', () => {
  let component: ChangesReviewComponent;
  let fixture: ComponentFixture<ChangesReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangesReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangesReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
