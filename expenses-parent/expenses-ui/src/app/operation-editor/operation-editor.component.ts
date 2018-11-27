import { Component, OnInit } from '@angular/core';
import { Entities } from '../dto/entity';
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
  selector: 'app-operation-editor',
  templateUrl: './operation-editor.component.html',
  styleUrls: ['./operation-editor.component.css']
})
export class OperationEditorComponent implements OnInit {

  entities: Entities;
  currencies: Array<Currency>;
  operationTypes: Array<OperationType>;
  operation: Operation = new Operation();
  availableCategories: Categories[] = new Array();
  chosenCategories: Categories = new Categories();
  emitterCtrl: FormControl;
  receiverCtrl: FormControl;
  refund: Refund = new Refund();

  newReceiver = false;
  newEmitter = false;


  constructor(private operationService: OperationService,
    private categoryService: CategoryService,
    private currencyService: CurrencyService,
    private entitiesService: EntitiesService,
    private router: Router,
    private snackBar: MatSnackBar,
    private cookies: CookieService) { }

  ngOnInit() {
    for (let i = 0; i < 3; i++) {
      this.chosenCategories.push(new Category());
      this.availableCategories.push(new Categories());
    }

    this.entitiesService.getAllEntities().subscribe(ents => {
      this.entities = ents;
    });

    this.operationService.getAllTypes().subscribe(ents => {
      this.operationTypes = ents;
    });

    this.categoryService.getAllCategoriesByLevel(0).subscribe(cats => {
      this.availableCategories[0] = cats;
    });

    this.currencyService.getCurrencies().subscribe(data => {
      this.currencies = data;
      this.operation.currency = 'EUR';
    });

    this.operation.effectiveDate = new Date();
  }

  onSelectCategory(level) {
    this.categoryService.getAllCategoriesByNomenclature(this.chosenCategories[level].nomenclature).subscribe(cats => {
      this.availableCategories[level + 1] = cats;
    });
  }

  saveOperation() {
    let lastCategory: Category = null;
    for (const cat of this.chosenCategories) {
      if (cat.id) {
        lastCategory = cat;
      }
    }
    this.operation.category = lastCategory;
    this.operationService.saveOperation(this.operation).subscribe(data => {

      this.snackBar.open('Saved !', 'Close', {
        duration: 1000
      }).afterDismissed().subscribe(() => {
        this.router.navigate(['/dashboard']);
      });
    }, (error: HttpErrorResponse) => {
      this.snackBar.open('Error, retry in a few moments !', 'Close', {
        duration: 3000
      });
    });

  }

  registerEntity(event, type) {
    this.operation[type] = event.target.value;
    this.entities.push(event.target.value);
    type === 'receiver' ? this.newReceiver = false : this.newEmitter = false;
  }

  onTypeChange() {
    console.log('AAAA');
    if (this.operation.type.regular) {
      this.operation.discriminator = 'R';
    } else if (this.operation.type.temporary) {
      this.operation.discriminator = 'T';
    } else {
      this.operation.discriminator = 'O';
    }
    if (this.operation.type.sign < 0) {

      const userId = +this.cookies.get('user');

      this.operation.emitter = this.entities.filter(e => e.agentId === userId)[0];
      console.log(this.operation.emitter);
    } else {
      console.log('AAAA');
    }
  }

  reset() {
    this.operation = new Operation();
  }
  saveRefund() {
    this.operation.reimbursments.push(this.refund);
    this.refund = new Refund();
  }

  openCategoryDialog(category?: Category) {

  }

}
