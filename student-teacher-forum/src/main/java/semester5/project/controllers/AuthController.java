package semester5.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.AppUser;
import semester5.project.service.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	String admin() {
		return "app.login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register(ModelAndView mav) {
		AppUser user = new AppUser();
		mav.getModel().put("user", user);
		mav.setViewName("app.register");
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	ModelAndView register(ModelAndView mav, @Valid AppUser user, BindingResult result) {
		mav.setViewName("app.register");
		if (!result.hasErrors()) {
			userService.register(user);
			mav.setViewName("redirect:/");
		}
		return mav;
	}
}
