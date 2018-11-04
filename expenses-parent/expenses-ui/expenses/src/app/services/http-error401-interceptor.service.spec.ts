import { TestBed } from '@angular/core/testing';

import { HttpError401Interceptor } from './http-error401-interceptor.service';

describe('TokenInterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpError401Interceptor = TestBed.get(HttpError401Interceptor);
    expect(service).toBeTruthy();
  });
});
