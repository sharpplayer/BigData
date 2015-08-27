package uk.co.icfuture.mvc.configuration.contentnegotiation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

public class AppContentNegotiationStrategy implements
		ContentNegotiationStrategy {

	@Override
	public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest)
			throws HttpMediaTypeNotAcceptableException {
		ArrayList<MediaType> types = new ArrayList<MediaType>();
		HttpServletRequest sr = webRequest
				.getNativeRequest(HttpServletRequest.class);
		String path = sr.getServletPath();
		if (path.equals("/")) {
			types.add(MediaType.TEXT_HTML);
		} else if (path.startsWith("/login")) {
			types.add(MediaType.TEXT_HTML);
		} else if (path.startsWith("/admin")) {
			types.add(MediaType.TEXT_HTML);
		} else {
			types.add(MediaType.APPLICATION_JSON);
		}
		return types;
	}

}
