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

  getAllCategories(level: number, parent?: number): Observable<Categories> {
    return <Observable<Categories>> this.http.get(`${environment.api_url}/category/level/${level}${parent ? '/parent/' + parent : ''}`);
  }
}
