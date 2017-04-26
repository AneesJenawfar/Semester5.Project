package semester5.project.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import semester5.project.model.entity.Interest;

@Repository
public interface InterestDao extends CrudRepository<Interest, Long> {

	Interest findOneByName(String name);
}
