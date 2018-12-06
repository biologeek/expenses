package io.biologeek.expenses.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.repositories.CategoryRepository;
import io.biologeek.expenses.services.validation.Validator;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private Validator<Category> categoryValidator;

	/**
	 * Retrieves {@link Category} related to passed nomenclature.
	 * 
	 * 
	 * @param level
	 * @param nomenclature
	 * @return
	 */
	public Category getCategoryByNomenclature(String nomenclature) {
		return categoryRepository.findByNomenclature(nomenclature);
	}

	/**
	 * Retrieves {@link Category} sons related to passed nomenclature.
	 * 
	 * 
	 * 
	 * @param level
	 * @param nomenclature
	 * @return
	 */
	public List<Category> getCategoriesByParentNomenclature(Integer level, Category parent) {
		if (level != null)
			return categoryRepository.getSubCategoriesByNomenclature(level, parent.getNomenclature());
		return null;
	}

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getTopLevelCategories() {
		return categoryRepository.listTopLevelCategories();
	}

	public List<Category> getCategoriesByLevel(int nomenclature) {
		return categoryRepository.getCategoriesByLevel(nomenclature);
	}

	public List<Category> getCategoriesByIds(Set<Long> set) {
		return categoryRepository.findAll(set);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category saveCategory(Category convert) throws BusinessException {
		try {
			this.categoryValidator.validate(convert);
			return this.categoryRepository.save(convert);
		} catch (ValidationException e) {
			throw new BusinessException(e.getMessage());
		}
	}

}
