import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Currency } from '../dto/currency';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  constructor(private http: HttpClient) { }


  public getCurrencies(): Observable<Array<Currency>> {
    return <Observable<Array<Currency>>> this.http.get(`${environment.api_url}/currencies`);
  }
}
