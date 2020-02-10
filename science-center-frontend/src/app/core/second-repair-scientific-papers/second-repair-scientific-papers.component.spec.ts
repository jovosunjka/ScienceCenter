import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondRepairScientificPapersComponent } from './second-repair-scientific-papers.component';

describe('SecondRepairScientificPapersComponent', () => {
  let component: SecondRepairScientificPapersComponent;
  let fixture: ComponentFixture<SecondRepairScientificPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondRepairScientificPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondRepairScientificPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
