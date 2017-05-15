package semester5.project.controllers;

import java.util.List;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Comment;
import semester5.project.model.entity.Post;
import semester5.project.model.entity.PostNotification;
import semester5.project.service.CommentService;
import semester5.project.service.FriendService;
import semester5.project.service.PostNotificationService;
import semester5.project.service.PostService;
import semester5.project.service.UserService;
import semester5.project.status.ActionStatus;

@Controller
public class CommentController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private FriendService friendService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private PostNotificationService postNotificationService;

	@Autowired
	private PolicyFactory htmlPolicy;

	@Value("${post.shared}")
	private String shared;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ModelAndView viewComments(ModelAndView mav, @RequestParam("id") Long id) {
		AppUser user = getUser();

		Post post = postService.get(id);

		List<Comment> comments = commentService.getComments(post);
		mav.getModel().put("comments", comments);
		System.out.println(comments);
		mav.getModel().put("user", user);
		mav.getModel().put("post", post);
		mav.setViewName("app.comments");
		return mav;
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveComment(@RequestParam("id") Long id, @RequestParam("text") String text) {
		AppUser user = getUser();
		Post post = postService.get(id);
		String cleanedText = htmlPolicy.sanitize(text);

		Comment comment = new Comment(cleanedText);
		comment.setUser(user);
		comment.setPost(post);
		commentService.save(comment);
		if (user != post.getUser()) {
			PostNotification notification = new PostNotification(user, post.getUser(), post, "comment");
			postNotificationService.save(notification);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/like", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> like(@RequestParam("id") Long id) {

		Post post = postService.get(id);
		AppUser user = getUser();

		post.addLike(user);
		postService.save(post);
		if (user != post.getUser()) {
			PostNotification notification = new PostNotification(user, post.getUser(), post, "like");
			postNotificationService.save(notification);
		}
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

	@RequestMapping(value = "/share", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ActionStatus> share(@RequestParam("id") Long id) {

		Post post = postService.get(id);
		AppUser user = getUser();
		if (user != post.getUser()) {
			PostNotification notification = new PostNotification(user, post.getUser(), post, "share");
			postNotificationService.save(notification);
		}
		List<AppUser> friends = friendService.getFreinds(user);

		if (friends == null) {
			ActionStatus status = new ActionStatus(shared);
			return new ResponseEntity<>(status, HttpStatus.OK);
		}

		for (AppUser friend : friends) {
			PostNotification notify = new PostNotification(user, friend, post, "shared");
			postNotificationService.save(notify);
		}

		ActionStatus status = new ActionStatus(shared);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public ModelAndView notifications(ModelAndView mav) {

		AppUser user = getUser();
		List<PostNotification> notifications = postNotificationService.getNotifications(user);
		mav.getModel().put("notifications", notifications);
		mav.setViewName("app.notification");
		return mav;
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PostNotification>> getNotify() {

		AppUser user = getUser();
		List<PostNotification> notifications = postNotificationService.getTopNotification(user);
		// Date d = new Date();
		// List<PostNotification> notifications =
		// postNotificationService.getNewNotification(d, user);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

}
