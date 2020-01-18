import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestsForReviewerComponent } from './requests-for-reviewer.component';

describe('RequestsForReviewerComponent', () => {
  let component: RequestsForReviewerComponent;
  let fixture: ComponentFixture<RequestsForReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestsForReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestsForReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
