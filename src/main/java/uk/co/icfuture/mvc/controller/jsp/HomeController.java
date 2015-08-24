package uk.co.icfuture.mvc.controller.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String indexAction(ModelMap model) {
		return "home";
	}

	@RequestMapping(value = { "/403" }, method = RequestMethod.GET)
	public String pageAction(ModelMap model) {
		return "403";
	}

}
