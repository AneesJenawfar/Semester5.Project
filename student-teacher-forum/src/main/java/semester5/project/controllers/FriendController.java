package semester5.project.controllers;

import java.util.List;

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
import semester5.project.model.entity.Friend;
import semester5.project.service.FriendService;
import semester5.project.service.UserService;

@Controller
public class FriendController {

	@Autowired
	private UserService userService;

	@Autowired
	private FriendService friendService;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/send-request", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> sendRequest(@RequestParam("id") Long id) {

		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		Friend friend = new Friend(user, friendUser);
		friendService.save(friend);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/accept-request", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> acceptRequest(@RequestParam("id") Long id) {

		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		Friend friend = friendService.getByUsers(friendUser, user);
		if (friend != null) {
			friend.setConfirm(true);
			friendService.save(friend);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/unfriend", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> unfriend(@RequestParam("id") Long id) {

		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		Friend friend = friendService.getByUsers(friendUser, user);

		if (friend != null) {
			friendService.delete(friend.getId());
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		friend = friendService.getByUsers(user, friendUser);
		friendService.delete(friend.getId());
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/friend-status/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> friendStatus(@PathVariable("id") Long id) {

		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		Friend friend = friendService.getByUsers(friendUser, user);

		if (friend != null && friend.getConfirm() == true) {
			return new ResponseEntity<>("Connected", HttpStatus.OK);

		} else if (friend != null && friend.getConfirm() == false) {
			return new ResponseEntity<>("Accept Request", HttpStatus.OK);
		}
		friend = friendService.getByUsers(user, friendUser);

		if (friend != null && friend.getConfirm() == true) {
			return new ResponseEntity<>("Connected", HttpStatus.OK);

		} else if (friend != null && friend.getConfirm() == false) {
			return new ResponseEntity<>("Request Sent", HttpStatus.OK);
		}

		return new ResponseEntity<>("Connect", HttpStatus.OK);
	}

	@RequestMapping(value = "/get-friends", method = RequestMethod.GET)
	public ModelAndView search(ModelAndView mav) {
		AppUser user = getUser();
		List<AppUser> friends = friendService.getFreinds(user);
		mav.getModel().put("type", "friends");
		mav.getModel().put("friends", friends);
		mav.setViewName("app.search");
		return mav;
	}

}
