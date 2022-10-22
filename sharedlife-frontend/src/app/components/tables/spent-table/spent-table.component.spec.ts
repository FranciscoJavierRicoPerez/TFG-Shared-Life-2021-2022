import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpentTableComponent } from './spent-table.component';

describe('SpentTableComponent', () => {
  let component: SpentTableComponent;
  let fixture: ComponentFixture<SpentTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpentTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
