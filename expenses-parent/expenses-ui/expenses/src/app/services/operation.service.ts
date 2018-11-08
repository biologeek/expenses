import { Injectable } from '@angular/core';
import { Operation } from '../dto/operation';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  constructor(private http: HttpClient, private cookie: CookieService) { }


  saveOperation(operation: Operation): Observable<Operation> {
    return <Observable<Operation>>this.http.post(`${environment.api_url}/account/${this.cookie.get('account')}/operation`, operation);
  }
}