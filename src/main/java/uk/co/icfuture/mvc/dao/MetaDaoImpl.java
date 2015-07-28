package uk.co.icfuture.mvc.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.model.Meta;

@Repository("metaDao")
public class MetaDaoImpl extends AbstractDao<Meta> implements MetaDao {

	public Meta saveMeta(Meta meta) {
		if (meta.getId() == 0) {
			return persist(meta);
		} else {
			return update(meta);
		}
	}

	public List<Meta> getMetas() {
		return getList();
	}

	public Set<Meta> findMetas(Set<Meta> metas) {
		HashSet<Meta> ret = new HashSet<Meta>();
		for (Meta m : metas) {
			if (m.getId() == 0) {
				List<Meta> l = getList("description", m.getDescription());
				if (l.size() == 0) {
					ret.add(persist(m));
				} else {
					ret.add(l.get(0));
				}
			} else {
				ret.add(m);
			}
		}
		return ret;
	}

}
