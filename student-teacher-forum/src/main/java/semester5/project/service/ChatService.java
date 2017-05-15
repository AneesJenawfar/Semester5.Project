package semester5.project.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Chat;
import semester5.project.model.repository.ChatDao;

@Service
public class ChatService {

	@Autowired
	private ChatDao chatDao;

	public void save(Chat chat) {
		chatDao.save(chat);
	}

	public List<Chat> getChats(AppUser user1, AppUser user2) {
		return chatDao.findByUser1AndUser2OrderByTimeAsc(user1, user2).stream().collect(Collectors.toList());
	}

	public List<Chat> getNewChats(Date time, AppUser user1, AppUser user2) {
		return chatDao.findBytimeAfterAndUser1AndUser2OrderByTimeAsc(time, user1, user2).stream()
				.collect(Collectors.toList());
	}

	public void delete(Long id) {
		chatDao.delete(id);
	}

	public List<AppUser> getChatList(AppUser user) {

		List<AppUser> users1 = chatDao.findUser1ByUser2(user).stream().distinct().collect(Collectors.toList());
		List<AppUser> users2 = chatDao.findUser2ByUser1(user).stream().distinct().collect(Collectors.toList());

		users1.addAll(users2);

		return users1;
	}
}
