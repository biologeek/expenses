package io.biologeek.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

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

	public List<Category> getCategoryByLevels(int nomenclature) {
		return categoryRepository.getCategoriesByLevel(nomenclature);
	}

}
