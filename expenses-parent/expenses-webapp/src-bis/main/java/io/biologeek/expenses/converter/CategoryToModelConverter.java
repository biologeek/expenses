package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Category;
import io.biologeek.expenses.api.beans.Nomenclature;

public class CategoryToModelConverter {

	public static io.biologeek.expenses.domain.beans.Category convert(Category category) {
		io.biologeek.expenses.domain.beans.Category result = new io.biologeek.expenses.domain.beans.Category();
		result.setCategoryPicture(category.getPicture());
		result.setId(category.getId());
		result.setLevel(category.getLevel());
		result.setName(category.getName());
		result.setNomenclature(category.getNomenclature());
		result.setCategoryPictureId(category.getPictureId());
		return result;
	}

}
