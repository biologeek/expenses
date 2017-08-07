package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.Category;
import io.biologeek.expenses.converter.CategoryConverter;
import io.biologeek.expenses.converter.OperationToApiConverter.OperationTypeConverter;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(method = RequestMethod.GET, path = "/{nomenc}")
	public ResponseEntity<List<Category>> getCategoriesByNomenclature(@PathVariable("nomenc") String nomenclature) {
		
		io.biologeek.expenses.domain.beans.Category parent = categoryService.getCategoriesByNomenclature(nomenclature);
		int level = 0;
		if (parent != null){
			level = parent.getLevel() + 1;
		}
		return new ResponseEntity<>(
				CategoryConverter.convert(categoryService.getCategoriesByParentNomenclature(level, parent)), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/level/{nomenc}")
	public ResponseEntity<List<Category>> getCategoriesByLevels(@PathVariable("nomenc") String nomenclature) {
		return new ResponseEntity<>(
				CategoryConverter.convert(
						categoryService.getCategoryByLevels(Integer.valueOf(nomenclature))
						),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public ResponseEntity<List<Category>> getTopCategories() {
		return new ResponseEntity<>(CategoryConverter.convert(categoryService.getTopLevelCategories()),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/types")
	public ResponseEntity<List<io.biologeek.expenses.api.beans.OperationType>> getTypes() {
		return new ResponseEntity<>(OperationTypeConverter.convert(Arrays.asList(OperationType.values())),
				HttpStatus.OK);
	}
	
	
}
