package uk.co.icfuture.mvc.model;

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

import org.hibernate.validator.constraints.NotEmpty;

import uk.co.icfuture.mvc.utils.Helper;

import com.google.common.base.Joiner;

@Entity
@Table(name = "tblstatement")
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	@Column(unique = true)
	private String statement;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Meta> meta = new HashSet<Meta>();

	public Statement() {
		// TODO Auto-generated constructor stub
	}

	public Statement(int id, String statement, String[] meta) {
		this.id = id;
		this.statement = statement;
		for (int i = 0; i < meta.length; i++) {
			this.meta.add(new Meta(i, meta[i]));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
