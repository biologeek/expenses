package io.biologeek.expenses.api.beans;

import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonAutoDetect
public class Category {

	private Long id;

	private String name;

	private String picture;
	private Integer pictureId;

	private Integer level;
	
	private String nomenclature;
	@JsonFormat(shape=Shape.OBJECT)
	private Category parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	//
	// public Category getSubCategoryByName(final String name) {
	// return getSubCategories().stream().filter(new Predicate<Category>() {
	//
	// public boolean test(Category t) {
	// return t.getName().equals(name);
	// }
	// }).findFirst().get();
	// }

	public String getNomenclature() {
		return nomenclature;
	}

	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}

}
