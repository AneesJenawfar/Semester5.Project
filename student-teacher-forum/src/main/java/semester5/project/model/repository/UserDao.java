package semester5.project.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import semester5.project.model.entity.AppUser;

@Repository
public interface UserDao extends CrudRepository<AppUser, Long> {
	AppUser findByEmail(String email);
}
