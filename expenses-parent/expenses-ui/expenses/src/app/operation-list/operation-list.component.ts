import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-operation-list',
  templateUrl: './operation-list.component.html',
  styleUrls: ['./operation-list.component.css']
})
export class OperationListComponent implements OnInit {



  operations = [
    {
      amount: 12.34,
      currency: '€',
      category: {
        name: 'Ménage',
        picture: 'a.png',
        nomenclature: '001-003'
      }
    },
    {
      amount: 12.34,
      currency: '€',
      category: {
        name: 'Ménage',
        picture: 'a.png',
        nomenclature: '001-003'
      }
    },
    {
      amount: 12.34,
      currency: '€',
      category: {
        name: 'Ménage',
        picture: 'a.png',
        nomenclature: '001-003'
      }
    }
  ];
  constructor() { }

  ngOnInit() {
  }

}
