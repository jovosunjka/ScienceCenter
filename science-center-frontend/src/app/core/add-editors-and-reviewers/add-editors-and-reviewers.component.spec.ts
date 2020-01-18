import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditorsAndReviewersComponent } from './add-editors-and-reviewers.component';

describe('AddEditorsAndReviewersComponent', () => {
  let component: AddEditorsAndReviewersComponent;
  let fixture: ComponentFixture<AddEditorsAndReviewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditorsAndReviewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditorsAndReviewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
