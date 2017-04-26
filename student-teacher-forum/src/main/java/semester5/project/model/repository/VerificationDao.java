package semester5.project.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import semester5.project.model.entity.VerificationToken;

@Repository
public interface VerificationDao extends CrudRepository<VerificationToken, Long> {

	VerificationToken findByToken(String Token);
}
