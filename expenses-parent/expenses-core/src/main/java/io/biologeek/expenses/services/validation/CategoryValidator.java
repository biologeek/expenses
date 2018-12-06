package io.biologeek.expenses.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.exceptions.ValidationException;

@Service
public class CategoryValidator implements Validator<Category> {

	@Override
	public void validate(Category toValidate) throws ValidationException {
		try {
			Assert.notNull(toValidate.getName(), "Category name null");
			Assert.notNull(toValidate.getLevel(), "Category level null");
		} catch (IllegalArgumentException e) {
			throw new ValidationException(e.getMessage());
		}
	}

}
