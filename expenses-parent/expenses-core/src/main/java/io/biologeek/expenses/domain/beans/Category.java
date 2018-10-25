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
 * 
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
	 * Picture file name used to illustrate category
	 */
	private String categoryPicture;
	
	/**
	 * Android picture ID
	 */
	private int categoryPictureId;

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

	public int getCategoryPictureId() {
		return categoryPictureId;
	}

	public void setCategoryPictureId(int categoryPictureId) {
		this.categoryPictureId = categoryPictureId;
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

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", level=" + level + ", nomenclature=" + nomenclature
				+ ", parent=" + parent + ", subCategories=" + subCategories + ", operations=" + operations
				+ ", organizations=" + organizations + ", categoryPicture=" + categoryPicture + ", categoryPictureId="
				+ categoryPictureId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryPicture == null) ? 0 : categoryPicture.hashCode());
		result = prime * result + categoryPictureId;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nomenclature == null) ? 0 : nomenclature.hashCode());
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		result = prime * result + ((organizations == null) ? 0 : organizations.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((subCategories == null) ? 0 : subCategories.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryPicture == null) {
			if (other.categoryPicture != null)
				return false;
		} else if (!categoryPicture.equals(other.categoryPicture))
			return false;
		if (categoryPictureId != other.categoryPictureId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nomenclature == null) {
			if (other.nomenclature != null)
				return false;
		} else if (!nomenclature.equals(other.nomenclature))
			return false;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		if (organizations == null) {
			if (other.organizations != null)
				return false;
		} else if (!organizations.equals(other.organizations))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (subCategories == null) {
			if (other.subCategories != null)
				return false;
		} else if (!subCategories.equals(other.subCategories))
			return false;
		return true;
	}

}
