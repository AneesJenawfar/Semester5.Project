package semester5.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.Comment;
import semester5.project.model.entity.Post;
import semester5.project.model.repository.CommentDao;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public List<Comment> getComments(Post post) {

		return commentDao.findByPostId(post.getId()).stream().collect(Collectors.toList());
	}

	public void delete(Long id) {
		commentDao.delete(id);

	}
}
