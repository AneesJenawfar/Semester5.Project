package semester5.project.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Chat;

public interface ChatDao extends CrudRepository<Chat, Long> {

	List<Chat> findByUser1AndUser2(AppUser user1, AppUser user2);

	List<Chat> findByUser1AndUser2OrderByTimeAsc(AppUser user1, AppUser user2);

	List<Chat> findBytimeAfterAndUser1AndUser2OrderByTimeAsc(Date time, AppUser user1, AppUser user2);

	@Query("SELECT c.user2 FROM Chat c where c.user1 = :user1")
	List<AppUser> findUser2ByUser1(@Param("user1") AppUser user1);

	@Query("SELECT c.user1 FROM Chat c where c.user2 = :user2")
	List<AppUser> findUser1ByUser2(@Param("user2") AppUser user2);
}
