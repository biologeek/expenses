package io.biologeek.expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.Category;
import io.biologeek.expenses.converter.CategoryToApiConverter;
import io.biologeek.expenses.converter.CategoryToModelConverter;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends ExceptionWrappedRestController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * Fids all sons of a category using its nomenclature (e.g : 001 or 002-001, ...)
	 * @param nomenclature 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{nomenc}")
	public ResponseEntity<List<Category>> getCategoriesByNomenclature(@PathVariable("nomenc") String nomenclature) {
		
		io.biologeek.expenses.domain.beans.Category parent = categoryService.getCategoryByNomenclature(nomenclature);
		int level = 0;
		if (parent != null){
			level = parent.getLevel() + 1;
		}
		return new ResponseEntity<>(
				CategoryToApiConverter.convert(categoryService.getCategoriesByParentNomenclature(level, parent)), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/level/{nomenc}")
	/**
	 * Finds all categories at a level (0, 1, 2, 3)
	 * @param nomenclature
	 * @return
	 */
	public ResponseEntity<List<Category>> getCategoriesByLevels(@PathVariable("nomenc") Integer nomenclature) {
		return new ResponseEntity<>(
				CategoryToApiConverter.convert(
						categoryService.getCategoriesByLevel(nomenclature)
						),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/top")
	public ResponseEntity<List<Category>> getTopCategories() {
		return new ResponseEntity<>(CategoryToApiConverter.convert(categoryService.getTopLevelCategories()),
				HttpStatus.OK);
	}	

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<>(CategoryToApiConverter.convert(categoryService.getAllCategories()),
				HttpStatus.OK);
	}	
	
	@PostMapping("")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		io.biologeek.expenses.domain.beans.Category result;
		try {
			result = this.categoryService.saveCategory(CategoryToModelConverter.convert(category));
		} catch (BusinessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(CategoryToApiConverter.convertToCategory(result), HttpStatus.CREATED);
	}
}
