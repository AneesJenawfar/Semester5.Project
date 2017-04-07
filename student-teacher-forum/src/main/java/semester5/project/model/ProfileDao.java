package semester5.project.model;

import org.springframework.data.repository.CrudRepository;

public interface ProfileDao extends CrudRepository<Profile, Long> {

	Profile findByUser(AppUser user);
}
