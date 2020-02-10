import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectReviewersForScientificPapersComponent } from './select-reviewers-for-scientific-papers.component';

describe('SelectReviewersForScientificPapersComponent', () => {
  let component: SelectReviewersForScientificPapersComponent;
  let fixture: ComponentFixture<SelectReviewersForScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectReviewersForScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectReviewersForScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
