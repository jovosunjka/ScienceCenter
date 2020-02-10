import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FirstDecisionComponent } from './first-decision.component';

describe('FirstDecisionComponent', () => {
  let component: FirstDecisionComponent;
  let fixture: ComponentFixture<FirstDecisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FirstDecisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FirstDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
