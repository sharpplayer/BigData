package uk.co.icfuture.mvc.service;

import java.util.List;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.Meta;

public interface MetaService {
	public Meta saveMeta(Meta meta);

	public List<Meta> getMetas();

	public Meta getMeta(int id) throws ItemNotFoundException;
}
