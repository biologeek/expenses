import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { ConnectionInformation } from 'src/app/dto/connection-information';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-connection',
  templateUrl: './connection.component.html',
  styleUrls: ['./connection.component.css']
})
export class ConnectionComponent implements OnInit {

  connectionInfo: ConnectionInformation;
  constructor(private userService: UserService, private cookies: CookieService, private router: Router) { }

  ngOnInit() {
    this.connectionInfo = new ConnectionInformation();
  }

  connect() {
    this.userService.connectUserWithCredentials(this.connectionInfo).subscribe(data => {
      this.cookies.set('user', '' + data.id);
      this.cookies.set('token', '' + data.sessionToken);

      this.router.navigate(['/dashboard']);
    }).unsubscribe();
  }

}
