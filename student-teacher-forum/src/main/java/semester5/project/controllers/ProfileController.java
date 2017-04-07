package semester5.project.controllers;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.AppUser;
import semester5.project.model.Profile;
import semester5.project.service.ProfileService;
import semester5.project.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private PolicyFactory htmlPolicy;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/profile")
	public ModelAndView showProfile(ModelAndView mav) {

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		mav.getModel().put("profile", webProfile);
		mav.setViewName("app.profile");
		return mav;
	}

	@RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
	public ModelAndView editProfile(ModelAndView mav) {

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		mav.getModel().put("profile", webProfile);
		mav.setViewName("app.editProfile");
		return mav;
	}

	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
	public ModelAndView editProfile(ModelAndView mav, @Valid Profile webProfile, BindingResult result) {

		mav.setViewName("app.editProfile");

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		profile.safeMergeFrom(webProfile, htmlPolicy);

		if (!result.hasErrors()) {
			profileService.save(profile);
			mav.setViewName("redirect:/profile");
		}

		return mav;
	}
}
