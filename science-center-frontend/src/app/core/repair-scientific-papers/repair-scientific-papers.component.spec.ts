import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepairScientificPapersComponent } from './repair-scientific-papers.component';

describe('RepairScientificPapersComponent', () => {
  let component: RepairScientificPapersComponent;
  let fixture: ComponentFixture<RepairScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepairScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepairScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
