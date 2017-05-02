package semester5.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Comment;
import semester5.project.model.entity.Post;
import semester5.project.service.CommentService;
import semester5.project.service.PostService;
import semester5.project.service.UserService;

@Controller
public class CommentController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	ModelAndView viewComments(ModelAndView mav, @RequestParam("id") Long id) {

		Comment comment = new Comment();
		Post post = postService.get(id);
		mav.getModel().put("comment", comment);

		List<Comment> comments = commentService.getComments(post);
		mav.getModel().put("comments", comments);

		mav.getModel().put("post", post);
		mav.setViewName("app.comments");
		return mav;
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	ModelAndView addComment(ModelAndView mav, @Valid Comment comment, @Valid Post post, BindingResult result) {

		AppUser user = getUser();
		mav.setViewName("redirect:/");
		if (!result.hasErrors()) {
			comment.setUser(user);
			comment.setPost(post);
			commentService.save(comment);
			mav.setViewName("redirect:/viewpost");
		}
		return mav;
	}

}
