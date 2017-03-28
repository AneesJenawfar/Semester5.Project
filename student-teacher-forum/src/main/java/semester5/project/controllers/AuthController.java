package semester5.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.AppUser;
import semester5.project.service.UserService;

// this annotation will make this call as a controller class
@Controller
public class AuthController {
	// this annotation will connect functions of services
	@Autowired
	private UserService userService;

	// Calling the url /login will be connected to this mapping function
	@RequestMapping("/login")
	String admin() {
		// this returns calling a tile definition
		return "app.login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register(ModelAndView mav) {
		AppUser user = new AppUser(); // creating a AppUser object
		mav.getModel().put("user", user); // put the object in the model
		mav.setViewName("app.register"); // set the model name to connect a tile
											// definition
		return mav; // returns the model
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	ModelAndView register(ModelAndView mav, @ModelAttribute(value = "user") @Valid AppUser user, BindingResult result) {
		mav.setViewName("app.register");
		if (!result.hasErrors()) {
			userService.register(user); // save the user detail by using
										// register function in UserService
			mav.setViewName("redirect:/");
		}
		return mav;
	}
}
