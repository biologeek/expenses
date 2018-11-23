import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Entities, Entity } from '../dto/entity';
import { FormControl } from '@angular/forms';
import { Operation, Interval, Refund } from '../dto/operation';
import { OperationService } from '../services/operation.service';
import { CategoryService } from '../services/category.service';
import { EntitiesService } from '../services/entities.service';
import { Category, Categories } from '../dto/category';
import { Observable, of } from 'rxjs';
import { CurrencyService } from '../services/currency.service';
import { Currency } from '../dto/currency';
import { OperationType } from '../dto/operation-type';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-entity-editor',
  templateUrl: './entity-editor.component.html',
  styleUrls: ['./entity-editor.component.css']
})
export class EntityEditorComponent implements OnInit {

  entity: Entity;

  @Input()
  simple: boolean;

  @Output()
  exitSuccess: EventEmitter<Entity>;

  constructor(
    private entitiesService: EntitiesService,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
  }


  onSave() {
    this.entitiesService.save(this.entity).subscribe(ent => {
      this.snackBar.open(`${ent.name} saved !`, 'Close', {
        duration: 1000
      });
      this.exitSuccess = new EventEmitter();
      this.exitSuccess.emit(ent);
    }, err => {
      this.snackBar.open(`Error, please retry in a few minutes`, 'Close', {
        duration: 3000
      });
    });
  }

}
