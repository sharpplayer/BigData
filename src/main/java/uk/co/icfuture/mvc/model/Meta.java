package uk.co.icfuture.mvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "tblmeta")
@XmlRootElement(name = "meta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meta implements Serializable {

	private static final long serialVersionUID = -4842398964185944L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "metaId")
	private int metaId;

	@Size(min = 1, max = 50)
	@Column(name = "description", unique = true)
	@Type(type = "uk.co.icfuture.mvc.utils.LowerCaseString")
	private String description;

	public Meta() {
	}

	public Meta(int metaId, String description) {
		this.metaId = metaId;
		this.description = description;
	}

	public int getMetaId() {
		return this.metaId;
	}

	public void setMetaId(int metaId) {
		this.metaId = metaId;
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
