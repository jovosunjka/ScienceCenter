import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondDecisionComponent } from './second-decision.component';

describe('SecondDecisionComponent', () => {
  let component: SecondDecisionComponent;
  let fixture: ComponentFixture<SecondDecisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondDecisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
