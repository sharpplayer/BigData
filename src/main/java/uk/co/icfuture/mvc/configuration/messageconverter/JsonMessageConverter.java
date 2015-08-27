package uk.co.icfuture.mvc.configuration.messageconverter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
//import org.eclipse.persistence.jaxb.MarshallerProperties;
//import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import uk.co.icfuture.mvc.utils.Helper;

public class JsonMessageConverter<T> extends AbstractHttpMessageConverter<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private JAXBContext jc;

	public JsonMessageConverter(JAXBContext jc) {
		super(new MediaType("application", "json", DEFAULT_CHARSET),
				new MediaType("application", "*+json", DEFAULT_CHARSET));
		this.jc = jc;
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	protected T readInternal(Class<? extends T> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		try {
			Unmarshaller u = jc.createUnmarshaller();
			u.setProperty(UnmarshallerProperties.MEDIA_TYPE,
					MediaType.APPLICATION_JSON);
			return Helper.uncheckedCast(u.unmarshal(inputMessage.getBody()));
		} catch (JAXBException e) {
			throw new HttpMessageNotReadableException("Could not read JSON: "
					+ e.getMessage(), e);
		}
	}

	@Override
	protected void writeInternal(T t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		Class<T> clazz = Helper.uncheckedCast(t.getClass());
		JAXBElement<T> q2 = new JAXBElement<T>(getNamespace(clazz), clazz, t);
		try {
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,
					org.eclipse.persistence.oxm.MediaType.APPLICATION_JSON);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(q2, outputMessage.getBody());
		} catch (PropertyException e) {
			throw new HttpMessageNotWritableException("Could not write JSON: "
					+ e.getMessage(), e);
		} catch (JAXBException e) {
			throw new HttpMessageNotWritableException("Could not write JSON: "
					+ e.getMessage(), e);
		}
	}

	private QName getNamespace(Class<T> clazz) {
		String name = clazz.getSimpleName();
		for (Annotation a : clazz.getAnnotations()) {
			if (a instanceof XmlRootElement) {
				String n = ((XmlRootElement) a).name();
				if (!n.isEmpty()) {
					name = n;
				}
			}
		}
		return new QName(name);
	}
}
