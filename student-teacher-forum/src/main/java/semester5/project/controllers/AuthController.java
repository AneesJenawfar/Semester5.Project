package semester5.project.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.AppUser;
import semester5.project.model.VerificationToken;
import semester5.project.service.EmailService;
import semester5.project.service.UserService;

// this annotation will make this call as a controller class
@Controller
public class AuthController {
	// this annotation will connect functions of services
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Value("${message.registration.confirmed}")
	private String confirmedMessage;

	@Value("${message.invalid.user}")
	private String invalidMessage;

	@Value("${message.expired.token}")
	private String expiredMessage;

	// Calling the url /login will be connected to this mapping function
	@RequestMapping("/login")
	String admin() {
		// this returns calling a tile definition
		return "app.login";
	}

	@RequestMapping("/verifyemail")
	String verifyEmail() {
		// this returns calling a tile definition
		return "app.verifyemail";
	}

	@RequestMapping("/confirmed")
	ModelAndView registrationConfirmed(ModelAndView mav, @RequestParam("t") String tokenString) {
		VerificationToken token = userService.getVerificationToken(tokenString);
		if (token == null) {
			userService.deleteToken(token);
			mav.setViewName("redirect:/invaliduser");
			return mav;
		}

		Date expiryDate = token.getExpiry();
		if (expiryDate.before(new Date())) {
			mav.setViewName("redirect:/expiredtoken");
			userService.deleteToken(token);
			return mav;
		}

		AppUser user = token.getUser();
		if (user == null) {
			mav.setViewName("redirect:/invaliduser");
			userService.deleteToken(token);
			return mav;
		}
		userService.deleteToken(token);
		user.setEnabled(true);
		userService.save(user);
		
		mav.getModel().put("message", confirmedMessage);
		mav.setViewName("app.message");
		return mav;
	}

	@RequestMapping("/invaliduser")
	ModelAndView invalidUser(ModelAndView mav) {
		mav.getModel().put("message", invalidMessage);
		mav.setViewName("app.message");
		return mav;
	}

	@RequestMapping("/expiredtoken")
	ModelAndView expiredToken(ModelAndView mav) {
		mav.getModel().put("message", expiredMessage);
		mav.setViewName("app.message");
		return mav;
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
			userService.register(user);
			String token = userService.createEmailVerificationToken(user);
			emailService.sendVarificationEmail(user.getEmail(), token);
			mav.setViewName("redirect:/verifyemail");

		}
		return mav;
	}
}
