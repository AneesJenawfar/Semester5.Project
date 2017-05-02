package semester5.project.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import semester5.project.model.entity.Comment;

public interface CommentDao extends CrudRepository<Comment, Long> {

	List<Comment> findByPostId(Long id);
}
