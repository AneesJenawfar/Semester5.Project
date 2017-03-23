package semester5.project.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<AppUser, Long> {
	AppUser findByEmail(String email);
}
