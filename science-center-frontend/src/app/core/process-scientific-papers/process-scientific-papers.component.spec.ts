import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessScientificPapersComponent } from './process-scientific-papers.component';

describe('ProcessScientificPapersComponent', () => {
  let component: ProcessScientificPapersComponent;
  let fixture: ComponentFixture<ProcessScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
