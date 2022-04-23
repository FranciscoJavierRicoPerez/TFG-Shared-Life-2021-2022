import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpentCreateComponent } from './spent-create.component';

describe('SpentCreateComponent', () => {
  let component: SpentCreateComponent;
  let fixture: ComponentFixture<SpentCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpentCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpentCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
