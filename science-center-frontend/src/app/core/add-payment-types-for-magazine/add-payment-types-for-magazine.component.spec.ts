import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPaymentTypesForMagazineComponent } from './add-payment-types-for-magazine.component';

describe('AddPaymentTypesForMagazineComponent', () => {
  let component: AddPaymentTypesForMagazineComponent;
  let fixture: ComponentFixture<AddPaymentTypesForMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPaymentTypesForMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPaymentTypesForMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
