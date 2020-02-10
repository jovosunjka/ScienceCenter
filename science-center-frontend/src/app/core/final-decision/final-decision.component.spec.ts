import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalDecisionComponent } from './final-decision.component';

describe('FinalDecisionComponent', () => {
  let component: FinalDecisionComponent;
  let fixture: ComponentFixture<FinalDecisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalDecisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
