import { Injectable } from '@angular/core';
import { Accounts } from '../dto/account';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private cookie: CookieService, private http: HttpClient) { }


  listAccounts(): Observable<Accounts> {
    return <Observable<Accounts>> this.http.get(`${environment.api_url}/user/${this.cookie.get('user')}/accounts`);
  }
}
