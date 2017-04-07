package semester5.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.Post;
import semester5.project.service.PostService;

@Controller
public class PageController {

	@Autowired
	private PostService postService;
	@Value("${message.access.denied}")
	private String accessDenied;

	@RequestMapping("/")
	ModelAndView Home(ModelAndView mav) {

		Post post = postService.getLatest();
		mav.getModel().put("post", post);
		mav.setViewName("app.homepage");
		Post latest = this.postService.getLatest();
		mav.getModel().put("latest", latest);
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
