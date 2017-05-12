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

	public List<Friend> getFreinds(AppUser user) {

		return friendDao.findByUser(user).stream().collect(Collectors.toList());
	}
}
