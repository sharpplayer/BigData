package uk.co.icfuture.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.icfuture.mvc.dao.MetaDao;
import uk.co.icfuture.mvc.model.Meta;

@Service("metaService")
@Transactional
public class MetaServiceImpl implements MetaService {

	@Autowired
	private MetaDao metaDao;

	public Meta saveMeta(Meta meta) {
		return metaDao.saveMeta(meta);
	}

	public List<Meta> getMetas() {
		return metaDao.getMetas();
	}

}
