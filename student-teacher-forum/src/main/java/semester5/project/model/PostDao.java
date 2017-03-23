package semester5.project.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends PagingAndSortingRepository<Post, Long> {
	Post findFirstByOrderByUpdatedDesc();
}
