package semester5.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	String Home() {
		return "app.homepage";
	}

	@RequestMapping("/about")
	String about() {
		return "app.about";
	}

	@RequestMapping("/addstatus")
	String addStatus() {
		return "app.addStatus";
	}
}
