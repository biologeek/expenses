import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Entities, Entity } from '../dto/entity';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {

  constructor(private http: HttpClient, private cookie: CookieService) { }

  getAllEntities(): Observable<Entities> {
    return <Observable<Entities>> this.http.get(`${environment.api_url}/entities/user/${this.cookie.get('user')}`);
  }

  save(entity: Entity): Observable<Entity> {
    return <Observable<Entity>> this.http.post(`${environment.api_url}/entities/user/${this.cookie.get('user')}`, entity);
  }
}
