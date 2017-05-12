package semester5.project.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Friend;

public interface FriendDao extends CrudRepository<Friend, Long> {

	List<Friend> findByUser(AppUser user);
}
