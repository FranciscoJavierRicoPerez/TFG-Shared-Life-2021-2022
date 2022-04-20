import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeInfoPageComponent } from './home-info-page.component';

describe('HomeInfoPageComponent', () => {
  let component: HomeInfoPageComponent;
  let fixture: ComponentFixture<HomeInfoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeInfoPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
