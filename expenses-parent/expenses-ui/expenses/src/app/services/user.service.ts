import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConnectionInformation } from '../dto/connection-information';
import { environment } from '../../environments/environment';
import { UserImage } from '../dto/user-image';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {}

  public connectUserWithCredentials(credentials: ConnectionInformation): Observable<UserImage> {
    return <Observable<UserImage>> this.http.post(`${environment.api_url}/user/login`, credentials);
  }

}
