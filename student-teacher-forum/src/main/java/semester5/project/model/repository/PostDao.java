package semester5.project.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import semester5.project.model.entity.Post;

@Repository
public interface PostDao extends PagingAndSortingRepository<Post, Long> {
	Post findFirstByOrderByUpdatedDesc();
}
