package semester5.project.controllers;

import java.io.IOException;
import java.util.List;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@Autowired
	private PolicyFactory htmlPolicy;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ModelAndView viewComments(ModelAndView mav, @RequestParam("id") Long id) {

		// Comment comment = new Comment();
		Post post = postService.get(id);
		// mav.getModel().put("comment", comment);

		List<Comment> comments = commentService.getComments(post);
		mav.getModel().put("comments", comments);

		mav.getModel().put("post", post);
		mav.setViewName("app.comments");
		return mav;
	}

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Comment>> servePhoto(@PathVariable("id") Long id) throws IOException {
		Post post = postService.get(id);
		List<Comment> comments = commentService.getComments(post);

		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/comment", method = RequestMethod.POST) public
	 * ModelAndView addComment(ModelAndView mav, @Valid Comment comment, @Valid
	 * Post post, BindingResult result) {
	 * 
	 * AppUser user = getUser(); mav.setViewName("redirect:/"); if
	 * (!result.hasErrors()) { comment.setUser(user); comment.setPost(post);
	 * commentService.save(comment); mav.setViewName("redirect:/viewpost"); }
	 * else { commentService.delete(comment.getId()); } return mav; }
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<Comment>> saveComment(@RequestParam("id") Long id, @RequestParam("text") String text) {
		AppUser user = getUser();
		Post post = postService.get(id);
		String cleanedText = htmlPolicy.sanitize(text);

		Comment comment = new Comment(cleanedText);
		comment.setUser(user);
		comment.setPost(post);
		commentService.save(comment);
		List<Comment> comments = commentService.getComments(post);

		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@RequestMapping(value = "/like", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> like(@RequestParam("id") Long id) {

		Post post = postService.get(id);
		AppUser user = getUser();

		post.addLike(user);
		postService.save(post);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/dislike", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> dislike(@RequestParam("id") Long id) {

		Post post = postService.get(id);
		AppUser user = getUser();

		post.removeLike(user);
		postService.save(post);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
