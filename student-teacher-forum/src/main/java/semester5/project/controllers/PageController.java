package semester5.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.service.PostService;

@Controller
public class PageController {

	@Autowired
	private PostService postService;

	@Value("${message.access.denied}")
	private String accessDenied;

	@RequestMapping("/")
	ModelAndView Home(ModelAndView mav) {

		mav.setViewName("app.homepage");
		return mav;
	}

	@RequestMapping("/about")
	String about() {
		return "app.about";
	}

	@RequestMapping("/403")
	ModelAndView expiredToken(ModelAndView mav) {
		mav.getModel().put("message", accessDenied);
		mav.setViewName("app.message");
		return mav;
	}
}
