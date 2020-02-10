import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificPapersInMagazineComponent } from './scientific-papers-in-magazine.component';

describe('ScientificPapersInMagazineComponent', () => {
  let component: ScientificPapersInMagazineComponent;
  let fixture: ComponentFixture<ScientificPapersInMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificPapersInMagazineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificPapersInMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
