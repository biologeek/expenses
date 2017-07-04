package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("from Category where level = :level and parent.nomenclature = :nomenc")
	List<Category> getSubCategoriesByNomenclature(@Param("level")Integer level, @Param("nomenc") String nomenclature);

	@Query("from Category where level = 0")
	List<Category> listTopLevelCategories();

	
	List<Category> getCategoriesByLevel(int level);

	Category findByNomenclature(String nomenclature);

	
}
