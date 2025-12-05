import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Compiler } from './compiler';

describe('Compiler', () => {
  let component: Compiler;
  let fixture: ComponentFixture<Compiler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Compiler]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Compiler);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
