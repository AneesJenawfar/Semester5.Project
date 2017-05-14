package semester5.project.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.PostNotification;

public interface PostNotificationDao extends CrudRepository<PostNotification, Long> {

	List<PostNotification> findByTargetUserOrderByTimeDesc(AppUser user);

	List<PostNotification> findFirst10ByTargetUserOrderByTimeDesc(AppUser user);

}
