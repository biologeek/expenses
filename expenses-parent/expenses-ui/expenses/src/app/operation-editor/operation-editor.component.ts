import { Component, OnInit } from '@angular/core';
import { Entities } from '../dto/entity';
import { FormControl } from '@angular/forms';
import { Operation } from '../dto/operation';
import { OperationService } from '../services/operation.service';
import { CategoryService } from '../services/category.service';
import { EntitiesService } from '../services/entities.service';

@Component({
  selector: 'app-operation-editor',
  templateUrl: './operation-editor.component.html',
  styleUrls: ['./operation-editor.component.css']
})
export class OperationEditorComponent implements OnInit {

  entities: Entities;
  operation: Operation = new Operation();

  emitterCtrl: FormControl;
  receiverCtrl: FormControl;

  constructor(private operationService: OperationService,
    private categoryService: CategoryService,
    private entitiesService: EntitiesService) { }

  ngOnInit() {
    this.entitiesService.getAllEntities().subscribe(ents => {
      this.entities = ents;
    })
    

  }


  saveOperation() {

    this.operationService.saveOperation(this.operation);

  }

}
