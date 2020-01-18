import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckDataForPendingMagazinesComponent } from './check-data-for-pending-magazines.component';

describe('CheckDataForPendingMagazinesComponent', () => {
  let component: CheckDataForPendingMagazinesComponent;
  let fixture: ComponentFixture<CheckDataForPendingMagazinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckDataForPendingMagazinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckDataForPendingMagazinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
