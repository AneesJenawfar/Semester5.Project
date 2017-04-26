package semester5.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.dto.SearchResult;
import semester5.project.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView search(ModelAndView mav, @RequestParam("s") String text,
			@RequestParam(name = "p", defaultValue = "1") int pageNumber) {

		Page<SearchResult> results = searchService.search(text, pageNumber);

		mav.getModel().put("s", text);
		mav.getModel().put("page", results);
		mav.setViewName("app.search");
		return mav;
	}

}
