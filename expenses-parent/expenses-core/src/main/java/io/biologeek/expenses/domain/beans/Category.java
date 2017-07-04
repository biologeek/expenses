package io.biologeek.expenses.domain.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * A Category is used to separate every operation depending on its type : food,
 * restaurant, transport, ... <br>
 * <br>
 * It implements a recursive relation in order to classify categories in a
 * nomenclature
 * 
 * @author xcaron
 *
 */
@Entity
public class Category {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	/**
	 * Level af the category in the classification
	 */
	private int level;
	
	private String nomenclature;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> subCategories;

	@OneToMany(mappedBy = "category")
	private List<Operation> operations;

	@OneToMany(mappedBy = "sector")
	private List<Organization> organizations;
	
	/**
	 * Picture used to illustrate category
	 */
	private String categoryPicture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryPicture() {
		return categoryPicture;
	}

	public void setCategoryPicture(String categoryPicture) {
		this.categoryPicture = categoryPicture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public String getNomenclature() {
		return nomenclature;
	}

	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

}
