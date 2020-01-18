import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazinesWithInvalidDataComponent } from './magazines-with-invalid-data.component';

describe('MagazinesWithInvalidDataComponent', () => {
  let component: MagazinesWithInvalidDataComponent;
  let fixture: ComponentFixture<MagazinesWithInvalidDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazinesWithInvalidDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazinesWithInvalidDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
