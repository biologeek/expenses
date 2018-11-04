import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-account-choice',
  templateUrl: './account-choice.component.html',
  styleUrls: ['./account-choice.component.css']
})
export class AccountChoiceComponent implements OnInit {


  accounts = [
    {
      id: 1,
      name: 'Account 1'
    }
  ];

  constructor(private cookies: CookieService, private router: Router, private accountService: AccountService) { }

  ngOnInit() {

    this.accountService.listAccounts().subscribe(acc => {
      this.accounts = acc;
    })
  }


  selectAccount($event: MatSelectChange) {
    this.cookies.set('account', $event.value.id);

    this.router.navigate(['/dashboard']);
  }

}
