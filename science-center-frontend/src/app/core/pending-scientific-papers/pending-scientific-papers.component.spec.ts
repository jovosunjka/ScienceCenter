import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingScientificPapersComponent } from './pending-scientific-papers.component';

describe('PendingScientificPapersComponent', () => {
  let component: PendingScientificPapersComponent;
  let fixture: ComponentFixture<PendingScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PendingScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
