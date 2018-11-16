import { Injectable } from '@angular/core';
import { Operation, Operations } from '../dto/operation';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { OperationType } from '../dto/operation-type';
import { PagedOperations } from '../dto/paged-operations';

@Injectable({
  providedIn: 'root'
})
export class OperationService {


  constructor(private http: HttpClient, private cookie: CookieService) { }


  getOperations(page?: number, limit?: number, orderBy?: string, reverse?: boolean): Observable<PagedOperations> {
    const obj: any = {};
    if (page) {
      obj.page = page.toString();
    }
    if (limit) {
      obj.limit = limit.toString();
    }
    if (orderBy) {
      obj.orderBy = orderBy;
    }
    if (reverse) {
      obj.reverse = reverse;
    }

    const prm: HttpParams = new HttpParams({
      fromObject: obj
    });
    return <Observable<PagedOperations>>this.http
      .get(`${environment.api_url}/operation/account/${this.cookie.get('account')}/operation`, { params: prm });
  }

  saveOperation(operation: Operation): Observable<Operation> {
    return <Observable<Operation>>this.http
      .post(`${environment.api_url}/operation/account/${this.cookie.get('account')}/operation`, operation);
  }


  public getAllTypes(): Observable<Array<OperationType>> {
    return <Observable<Array<OperationType>>>this.http.get(`${environment.api_url}/operation/types`);
  }
}
