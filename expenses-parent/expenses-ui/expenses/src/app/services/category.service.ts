import { Injectable } from '@angular/core';
import { Categories } from '../dto/category';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getAllCategoriesByNomenclature(nomenc: string): Observable<Categories> {
    return <Observable<Categories>> this.http.get(`${environment.api_url}/category/${nomenc}`);
  }
  getAllCategoriesByLevel(level: number): Observable<Categories> {
    return <Observable<Categories>> this.http.get(`${environment.api_url}/category/level/${level}`);
  }
}
