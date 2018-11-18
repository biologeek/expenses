import { Component, OnInit } from '@angular/core';
import { OperationService } from '../services/operation.service';
import { CookieService } from 'ngx-cookie-service';
import { Operations } from '../dto/operation';

@Component({
  selector: 'app-operation-list',
  templateUrl: './operation-list.component.html',
  styleUrls: ['./operation-list.component.css']
})
export class OperationListComponent implements OnInit {
  operations: Operations;

  constructor(private operationService: OperationService, private cookies: CookieService) { }

  ngOnInit() {

    this.operationService.getOperations().subscribe(op => {
      this.operations = op.operations;
    });
  }

}
