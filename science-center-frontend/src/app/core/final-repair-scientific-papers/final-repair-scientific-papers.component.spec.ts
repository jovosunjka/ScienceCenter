import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalRepairScientificPapersComponent } from './final-repair-scientific-papers.component';

describe('FinalRepairScientificPapersComponent', () => {
  let component: FinalRepairScientificPapersComponent;
  let fixture: ComponentFixture<FinalRepairScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalRepairScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalRepairScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
