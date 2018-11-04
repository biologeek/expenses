import { Component, OnInit } from '@angular/core';
import { Entities } from '../dto/entity';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-operation-editor',
  templateUrl: './operation-editor.component.html',
  styleUrls: ['./operation-editor.component.css']
})
export class OperationEditorComponent implements OnInit {

  entities: Entities;

  emitterCtrl: FormControl;
  receiverCtrl: FormControl;

  constructor() { }

  ngOnInit() {
    this.entities = [{
      name: 'A1'
    }, {
      name: 'A2'
    }, {
      name: 'A3'
    }];

  }

}
