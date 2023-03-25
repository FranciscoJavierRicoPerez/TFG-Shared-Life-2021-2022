import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeChatPageComponent } from './home-chat-page.component';

describe('HomeChatPageComponent', () => {
  let component: HomeChatPageComponent;
  let fixture: ComponentFixture<HomeChatPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeChatPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeChatPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
