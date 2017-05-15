package semester5.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Friend;
import semester5.project.model.repository.FriendDao;

@Service
public class FriendService {

	@Autowired
	private FriendDao friendDao;

	public void save(Friend friend) {
		friendDao.save(friend);
	}

	public void delete(Long id) {
		friendDao.delete(id);
	}

	public Friend get(Long id) {
		return friendDao.findOne(id);
	}

	public Friend getByUsers(AppUser sender, AppUser accepter) {
		return friendDao.findBySenderAndAccepter(sender, accepter);
	}

	public List<AppUser> getFreinds(AppUser user) {

		List<AppUser> users1 = friendDao.findSenderByAccepter(user).stream().distinct().collect(Collectors.toList());
		List<AppUser> users2 = friendDao.findAccepterBySender(user).stream().distinct().collect(Collectors.toList());

		users1.addAll(users2);
		return users1;
	}
}
