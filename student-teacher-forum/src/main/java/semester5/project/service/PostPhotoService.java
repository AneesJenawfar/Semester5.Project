package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.Post;
import semester5.project.model.entity.PostPhoto;
import semester5.project.model.repository.PostPhotoDao;

@Service
public class PostPhotoService {

	@Autowired
	private PostPhotoDao postPhotoDao;

	public void save(PostPhoto postPhoto) {
		postPhotoDao.save(postPhoto);
	}

	public void delete(Long id) {
		postPhotoDao.delete(id);

	}

	public PostPhoto get(Long id) {
		return postPhotoDao.findOne(id);
	}

	public PostPhoto getPostPhoto(Post post) {

		return postPhotoDao.findByPost(post);
	}

}
