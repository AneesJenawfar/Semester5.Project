package semester5.project.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Friend;

public interface FriendDao extends CrudRepository<Friend, Long> {

	Friend findBySenderAndAccepter(AppUser sender, AppUser accepter);

	@Query("SELECT f.sender FROM Friend f where f.accepter = :accepter")
	List<AppUser> findSenderByAccepter(@Param("accepter") AppUser accepter);

	@Query("SELECT f.accepter FROM Friend f where f.sender = :sender")
	List<AppUser> findAccepterBySender(@Param("sender") AppUser sender);
}
