package semester5.project.controllers;

import java.util.Date;
import java.util.List;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import semester5.project.model.entity.Chat;
import semester5.project.service.ChatService;
import semester5.project.service.UserService;

@Controller
public class ChatController {

	@Autowired
	private UserService userService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private PolicyFactory htmlPolicy;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/saveChat", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveChat(@RequestParam("id") Long id, @RequestParam("text") String text,
			@RequestParam("place") String place) {
		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		String cleanedText = htmlPolicy.sanitize(text);
		String clearPlace = htmlPolicy.sanitize(place);

		if (clearPlace.equals("first")) {
			Chat chat = new Chat(user, friendUser, cleanedText, true);
			chatService.save(chat);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		if (clearPlace.equals("second")) {
			Chat chat = new Chat(friendUser, user, cleanedText, false);
			chatService.save(chat);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		Chat chat = new Chat(user, friendUser, cleanedText, true);
		chatService.save(chat);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/chats", method = RequestMethod.GET)
	public ModelAndView viewChats(ModelAndView mav, @RequestParam("id") Long id) {
		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		mav.setViewName("app.chat");
		List<Chat> chats1 = chatService.getChats(user, friendUser);
		if (!chats1.isEmpty()) {
			mav.getModel().put("place", "first");
			mav.getModel().put("id", id);
			return mav;
		}

		List<Chat> chats2 = chatService.getChats(friendUser, user);
		if (!chats2.isEmpty()) {
			mav.getModel().put("place", "second");
			mav.getModel().put("id", id);
			return mav;
		}
		mav.getModel().put("place", "");
		mav.getModel().put("id", id);
		return mav;
	}

	@RequestMapping(value = "/get-chats", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Chat>> getChat(@RequestParam("id") Long id) {

		AppUser user = getUser();
		AppUser friendUser = userService.get(id);

		List<Chat> chats1 = chatService.getChats(user, friendUser);
		if (!chats1.isEmpty()) {

			return new ResponseEntity<>(chats1, HttpStatus.OK);
		}
		List<Chat> chats2 = chatService.getChats(friendUser, user);
		if (!chats2.isEmpty()) {
			return new ResponseEntity<>(chats2, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/new-chats", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Chat>> getNewChats(@RequestParam("id") Long id, @RequestParam("place") String place) {
		AppUser user = getUser();
		AppUser friendUser = userService.get(id);
		String clearPlace = htmlPolicy.sanitize(place);
		Date time = new Date(System.currentTimeMillis() - 3 * 1000);

		if (clearPlace.equals("first")) {
			List<Chat> chats1 = chatService.getNewChats(time, user, friendUser);

			return new ResponseEntity<>(chats1, HttpStatus.OK);
		}
		if (clearPlace.equals("second")) {
			List<Chat> chats2 = chatService.getNewChats(time, friendUser, user);

			return new ResponseEntity<>(chats2, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/chat-list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<AppUser>> getChatsList() {
		AppUser user = getUser();
		List<AppUser> users = chatService.getChatList(user);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
