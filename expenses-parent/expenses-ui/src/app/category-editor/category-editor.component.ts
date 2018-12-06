import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Categories, Category } from '../dto/category';
import { CategoryService } from '../services/category.service';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-category-editor',
  templateUrl: './category-editor.component.html',
  styleUrls: ['./category-editor.component.css']
})
export class CategoryEditorComponent implements OnInit {


  @Input()
  simple: boolean;

  category: Category = new Category();

  @Input()
  parent: Category = new Category();

  @Output()
  saveSuccess: EventEmitter<Category> = new EventEmitter();

  availableCategories$: Observable<Categories>;
  constructor(private snackBar: MatSnackBar, private categoryService: CategoryService) { }

  ngOnInit() {
    this.availableCategories$ = this.categoryService.getAllCategoriesFlat();
  }

  onSave() {

    if (this.parent) {
      this.category.parent = this.parent;
    }
    this.category.level = this.parent.level ? this.parent.level + 1 : 0;
    this.categoryService.saveCategory(this.category).subscribe(result => {
      this.snackBar.open(result.name + ' saved !',  'Close', {
        duration: 1000
      }).afterDismissed().subscribe(() => {
        this.saveSuccess.emit(result);
      });
    }, (error: HttpErrorResponse) => {
      this.snackBar.open('Error, retry in a few moments !', 'Close', {
        duration: 3000
      });
    });
  }

}
