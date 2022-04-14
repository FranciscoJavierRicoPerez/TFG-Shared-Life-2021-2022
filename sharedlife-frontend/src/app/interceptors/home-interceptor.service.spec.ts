import { TestBed } from '@angular/core/testing';

import { HomeInterceptorService } from './home-interceptor.service';

describe('HomeInterceptorService', () => {
  let service: HomeInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomeInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
