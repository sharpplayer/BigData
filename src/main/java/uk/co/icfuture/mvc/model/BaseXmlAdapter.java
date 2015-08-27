package uk.co.icfuture.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.core.GenericTypeResolver;

import uk.co.icfuture.mvc.utils.Helper;

public abstract class BaseXmlAdapter<T extends BaseXmlAdapter<?> & Serializable>
		extends XmlAdapter<T, T> implements Cloneable {

	@XmlTransient
	@Transient
	private List<T> xmlEntityList = new ArrayList<T>();

	@XmlTransient
	@Transient
	private Map<String, T> xmlEntityMap = new HashMap<String, T>();

	@XmlAttribute
	@Transient String reference;

	@XmlTransient
	@Transient
	private Class<T> genericClass;

	@Transient
	public abstract int getSerializationReference();

	public BaseXmlAdapter() {
		this.genericClass = Helper.uncheckedCast(GenericTypeResolver
				.resolveTypeArgument(getClass(), BaseXmlAdapter.class));
	}

	@Override
	public T unmarshal(T v) throws Exception {
		return null;
	}

	@Override
	public T marshal(T v) throws Exception {
		if (v != null) {
			if (xmlEntityList.contains(v)) {
				T adaptedEntity = genericClass.newInstance();
				adaptedEntity.reference = Integer.toString(v.getSerializationReference());
				return adaptedEntity;
			} else {
				T adaptedEntity = Helper.uncheckedCast(v.clone());
				xmlEntityList.add(v);
				return adaptedEntity;
			}
		}
		return null;
	}
}
