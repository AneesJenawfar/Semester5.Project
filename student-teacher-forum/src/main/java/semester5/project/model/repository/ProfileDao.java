package semester5.project.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Profile;

public interface ProfileDao extends CrudRepository<Profile, Long> {

	Profile findByUser(AppUser user);

	List<Profile> findByInterestsNameContainingIgnoreCase(String text);

	Page<Profile> findByInterestsNameContainingIgnoreCase(String text, Pageable request);

	Page<Profile> findByUserSurnameContainingIgnoreCase(String text, Pageable request);

	Page<Profile> findByUserFirstnameContainingIgnoreCase(String text, Pageable request);
}
