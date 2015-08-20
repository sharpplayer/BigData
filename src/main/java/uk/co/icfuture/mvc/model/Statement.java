package uk.co.icfuture.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.NotEmpty;

import uk.co.icfuture.mvc.utils.Helper;

import com.google.common.base.Joiner;

@Entity
@Table(name = "tblstatement")
public class Statement implements Serializable {

	private static final long serialVersionUID = 8898692435410781883L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "statementId", unique = true)
	private int statementId;

	@NotEmpty
	@Column(name = "statement", unique = true)
	private String statement = "";

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<Meta> meta = new HashSet<Meta>();

	public Statement() {
		// TODO Auto-generated constructor stub
	}

	public Statement(String s) {
		this.statement = s;
	}

	public Statement(int statementId, String statement, String[] meta) {
		this.statementId = statementId;
		this.statement = statement;
		for (int i = 0; i < meta.length; i++) {
			this.meta.add(new Meta(i, meta[i]));
		}
	}

	public Statement merge(Statement statement) {
		setStatement(statement.statement);
		setMetaStrings(statement.getMetaStrings());
		return this;
	}

	public int getStatementId() {
		return this.statementId;
	}

	public void setStatementId(int statementId) {
		this.statementId = statementId;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Set<Meta> getMeta() {
		return meta;
	}

	public void setMeta(Set<Meta> meta) {
		Helper.mergeCollection(this.meta, meta, false);
	}

	public void setMetaStrings(String meta) {
		String[] splits = meta.split(",");
		HashSet<Meta> metas = new HashSet<Meta>();
		for (String split : splits) {
			if (!split.trim().isEmpty()) {
				Meta m = new Meta();
				m.setDescription(split.trim());
				metas.add(m);
			}
		}
		if (this.meta.size() == 0) {
			Hibernate.initialize(this.getMeta());
		}
		Helper.mergeCollection(this.meta, metas, true);
	}

	@Transient
	public String getMetaStrings() {
		ArrayList<Meta> clone = new ArrayList<Meta>(this.meta);
		Collections.sort(clone, new Comparator<Meta>() {

			public int compare(Meta o1, Meta o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}

		});
		return Joiner.on(",").join(clone);
	}

	@Transient
	public boolean isEmpty() {
		return getStatement().isEmpty();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Statement) {
			Statement s = (Statement) obj;
			if (s.statement.equals(this.statement)) {
				return s.statementId == this.statementId;
			} else {
				return false;
			}
		}
		return false;
	}
}
