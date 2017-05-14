package semester5.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.PostNotification;
import semester5.project.model.repository.PostNotificationDao;

@Service
public class PostNotificationService {

	@Autowired
	private PostNotificationDao postNotificationDao;

	public void save(PostNotification notification) {
		postNotificationDao.save(notification);
	}

	public List<PostNotification> getNotifications(AppUser user) {

		return postNotificationDao.findByTargetUserOrderByTimeDesc(user).stream().collect(Collectors.toList());
	}

	public List<PostNotification> getNewNotification(AppUser user) {

		return postNotificationDao.findFirst10ByTargetUserOrderByTimeDesc(user).stream().collect(Collectors.toList());
	}

	public List<PostNotification> getcheck(AppUser user) {
		return postNotificationDao.findFirst10ByTargetUserOrderByTimeDesc(user).stream().collect(Collectors.toList());
	}

	public void delete(Long id) {
		postNotificationDao.delete(id);

	}
}
