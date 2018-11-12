import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ConnectionInformation } from '../dto/connection-information';
import { environment } from '../../environments/environment';
import { UserImage } from '../dto/user-image';
import { Observable } from 'rxjs/Observable';
import { AuthorizationInterceptorService } from './authorization-interceptor.service';
import { tap, map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private interceptor: AuthorizationInterceptorService, private cookies: CookieService) {}

  public connectUserWithCredentials(credentials: ConnectionInformation): Observable<UserImage> {
    return this.http.post(`${environment.api_url}/user/login`, credentials, { observe: 'response'})
    .pipe(tap((resp: HttpResponse<UserImage>) => {
      this.cookies.set('Authorization', resp.headers.get('Authorization'));
    })).pipe(map(resp => resp.body));
  }

}
