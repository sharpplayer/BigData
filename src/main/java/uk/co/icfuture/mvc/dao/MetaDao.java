package uk.co.icfuture.mvc.dao;

import java.util.List;
import java.util.Set;

import uk.co.icfuture.mvc.model.Meta;

public interface MetaDao {

	public Meta saveMeta(Meta meta);

	public List<Meta> getMetas();

	public Set<Meta> findMetas(Set<Meta> metas);
}
