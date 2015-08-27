package uk.co.icfuture.mvc.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.Meta;
import uk.co.icfuture.mvc.service.MetaService;

@RestController
@RequestMapping("/meta")
public class MetaResource {

	private MetaService metaService;

	@Autowired
	public MetaResource(MetaService ms) {
		this.metaService = ms;
	}

	@RequestMapping(value = { "{id}" }, method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public Meta getQuestion(@PathVariable("id") int id)
			throws NumberFormatException, ItemNotFoundException {
		return metaService.getMeta(id);
	}
}
