package semester5.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.StatusUpdate;
import semester5.project.service.StatusUpdateService;

@Controller
public class PageController {

	@Autowired
	private StatusUpdateService statusUpdateService;

	@RequestMapping("/")
	ModelAndView Home(ModelAndView mav) {

		StatusUpdate statusUpdate = statusUpdateService.getLatest();
		mav.getModel().put("statusUpdate", statusUpdate);
		mav.setViewName("app.homepage");
		return mav;
	}

	@RequestMapping("/about")
	String about() {
		return "app.about";
	}

}
