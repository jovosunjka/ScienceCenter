import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewScientificPapersComponent } from './review-scientific-papers.component';

describe('ReviewScientificPapersComponent', () => {
  let component: ReviewScientificPapersComponent;
  let fixture: ComponentFixture<ReviewScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
