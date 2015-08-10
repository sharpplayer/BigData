package uk.co.icfuture.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String indexAction(ModelMap model) {
		return "home";
	}
}
