import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FirstRepairScientificPapersComponent } from './first-repair-scientific-papers.component';

describe('FirstRepairScientificPapersComponent', () => {
  let component: FirstRepairScientificPapersComponent;
  let fixture: ComponentFixture<FirstRepairScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FirstRepairScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FirstRepairScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
