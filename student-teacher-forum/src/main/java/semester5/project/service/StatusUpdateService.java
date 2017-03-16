package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import semester5.project.model.StatusUpdate;
import semester5.project.model.StatusUpdateDao;

@Service
public class StatusUpdateService {

	private final static int pageSize = 5;

	@Autowired
	private StatusUpdateDao dao;

	public void save(StatusUpdate status) {
		dao.save(status);
	}

	public StatusUpdate getLatest() {
		return dao.findFirstByOrderByUpdatedDesc();
	}

	public Page<StatusUpdate> getPage(int pagenumber) {
		PageRequest request = new PageRequest(pagenumber - 1, pageSize, Sort.Direction.DESC, "updated");
		return dao.findAll(request);
	}

	public void delete(Long id) {
		dao.delete(id);

	}

	public StatusUpdate get(Long id) {

		return dao.findOne(id);
	}
}
