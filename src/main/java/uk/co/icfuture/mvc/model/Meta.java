package uk.co.icfuture.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "tblmeta")
public class Meta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Size(min = 1, max = 50)
	@Column(unique = true)
	@Type(type = "uk.co.icfuture.mvc.utils.LowerCaseString")
	private String description;

	public Meta() {
	}

	public Meta(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Meta) {
			return getDescription().equals(((Meta) arg0).getDescription());
		} else {
			return getDescription().equals(arg0);
		}
	}
}
