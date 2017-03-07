package semester5.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	ModelAndView addStatus(ModelAndView mav) {
		mav.setViewName("app.addStatus");
		StatusUpdate status = new StatusUpdate();
		mav.getModel().put("statusUpdate", status);
		return mav;
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	ModelAndView addStatus(ModelAndView mav, StatusUpdate status) {

		mav.setViewName("app.addStatus");
		statusUpdateService.save(status);

		return mav;
	}

}
