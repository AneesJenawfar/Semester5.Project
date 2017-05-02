package semester5.project.controllers;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Post;
import semester5.project.service.PostService;
import semester5.project.service.UserService;

@Controller
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private PolicyFactory htmlPolicy;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/addpost", method = RequestMethod.GET)
	ModelAndView addPost(ModelAndView mav, @ModelAttribute("post") Post post) {
		mav.setViewName("app.addPost");
		return mav;
	}

	@RequestMapping(value = "/addpost", method = RequestMethod.POST)
	ModelAndView addPost(ModelAndView mav, @Valid Post post, BindingResult result) {

		AppUser user = getUser();
		post.setUser(user);
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

		Post webPost = new Post();
		webPost.safeCopyFrom(post);

		mav.getModel().put("post", webPost);
		mav.getModel().put("postId", id);
		mav.setViewName("app.editPost");
		return mav;
	}

	@RequestMapping(value = "/editpost", method = RequestMethod.POST)
	ModelAndView editPost(ModelAndView mav, @Valid Post webPost, @Valid Long id, BindingResult result) {

		mav.setViewName("app.editPost");

		Post post = postService.get(id);
		post.safeMergeFrom(webPost, htmlPolicy);

		if (!result.hasErrors()) {
			postService.save(post);
			mav.setViewName("redirect:/viewpost");
		}
		return mav;
	}

}
