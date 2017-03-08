package semester5.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.StatusUpdate;
import semester5.project.service.StatusUpdateService;

@Controller
public class PageController {

	@Autowired
	private StatusUpdateService statusUpdateService;

	@RequestMapping("/")
	String Home() {
		return "app.homepage";
	}

	@RequestMapping("/about")
	String about() {
		return "app.about";
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.GET)
	ModelAndView addStatus(ModelAndView mav, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {
		mav.setViewName("app.addStatus");
		StatusUpdate latest = this.statusUpdateService.getLatest();
		mav.getModel().put("latest", latest);
		return mav;
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	ModelAndView addStatus(ModelAndView mav, @Valid StatusUpdate statusUpdate, BindingResult result) {

		mav.setViewName("app.addStatus");
		if (!result.hasErrors()) {
			statusUpdateService.save(statusUpdate);
			mav.getModel().put("statusUpdate", new StatusUpdate());
		}

		StatusUpdate latest = this.statusUpdateService.getLatest();
		mav.getModel().put("latest", latest);

		return mav;
	}

}
