package semester5.project.model.repository;

import org.springframework.data.repository.CrudRepository;

import semester5.project.model.entity.Post;
import semester5.project.model.entity.PostPhoto;

public interface PostPhotoDao extends CrudRepository<PostPhoto, Long> {

	PostPhoto findByPost(Post post);

}
