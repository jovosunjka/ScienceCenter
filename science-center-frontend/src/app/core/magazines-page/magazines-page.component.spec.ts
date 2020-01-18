import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazinesPageComponent } from './magazines-page.component';

describe('MagazinesPageComponent', () => {
  let component: MagazinesPageComponent;
  let fixture: ComponentFixture<MagazinesPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazinesPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazinesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
