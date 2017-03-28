package semester5.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.Post;
import semester5.project.service.PostService;

@Controller
public class PostController {
	@Autowired
	private PostService postService;

	@RequestMapping(value = "/addpost", method = RequestMethod.GET)
	ModelAndView addPost(ModelAndView mav, @ModelAttribute("post") Post post) {
		mav.setViewName("app.addPost");
		return mav;
	}

	@RequestMapping(value = "/addpost", method = RequestMethod.POST)
	ModelAndView addPost(ModelAndView mav, @Valid Post post, BindingResult result) {

		mav.setViewName("app.addPost");
		if (!result.hasErrors()) {
			postService.save(post);
			mav.getModel().put("post", new Post());
			mav.setViewName("redirect:/viewpost");
		}
		return mav;
	}

	@RequestMapping(value = "/viewpost", method = RequestMethod.GET)
	ModelAndView viewPost(ModelAndView mav, @RequestParam(name = "p", defaultValue = "1") int pagenumber) {

		Page<Post> page = postService.getPage(pagenumber);
		mav.getModel().put("page", page);
		mav.setViewName("app.viewPost");
		return mav;
	}

	@RequestMapping(value = "/deletepost", method = RequestMethod.GET)
	ModelAndView deletePost(ModelAndView mav, @RequestParam(name = "id") Long id) {
		postService.delete(id);
		mav.setViewName("redirect:/viewpost");
		return mav;
	}

	@RequestMapping(value = "/editpost", method = RequestMethod.GET)
	ModelAndView editPost(ModelAndView mav, @RequestParam(name = "id") Long id) {
		Post post = postService.get(id);
		mav.getModel().put("post", post);
		mav.setViewName("app.editPost");
		return mav;
	}

	@RequestMapping(value = "/editpost", method = RequestMethod.POST)
	ModelAndView editPost(ModelAndView mav, @Valid Post post, BindingResult result) {

		mav.setViewName("app.editPost");
		if (!result.hasErrors()) {
			postService.save(post);
			mav.setViewName("redirect:/viewpost");
		}
		return mav;
	}

}