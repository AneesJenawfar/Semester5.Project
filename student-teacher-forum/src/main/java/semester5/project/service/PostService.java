package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import semester5.project.model.Post;
import semester5.project.model.PostDao;

@Service
public class PostService {

	private final static int pageSize = 5;

	@Autowired
	private PostDao postDao;

	public void save(Post post) {
		postDao.save(post);
	}

	public Post getLatest() {
		return postDao.findFirstByOrderByUpdatedDesc();
	}

	public Page<Post> getPage(int pagenumber) {
		PageRequest request = new PageRequest(pagenumber - 1, pageSize, Sort.Direction.DESC, "updated");
		return postDao.findAll(request);
	}

	public void delete(Long id) {
		postDao.delete(id);

	}

	public Post get(Long id) {

		return postDao.findOne(id);
	}
}
