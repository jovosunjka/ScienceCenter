import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditorsComponent } from './add-editors.component';

describe('AddEditorsComponent', () => {
  let component: AddEditorsComponent;
  let fixture: ComponentFixture<AddEditorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
