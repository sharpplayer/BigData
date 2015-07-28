package uk.co.icfuture.mvc.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class LowerCaseString extends AbstractStandardBasicType<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8936086043748417191L;

	public LowerCaseString() {
		super(VarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			boolean[] settable, SessionImplementor session)
			throws HibernateException, SQLException {
		super.nullSafeSet(st, value != null ? ((String) value).toLowerCase()
				: null, index, session);

	}

	public String getName() {
		return getClass().getCanonicalName();
	}

}
