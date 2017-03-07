package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.StatusUpdate;
import semester5.project.model.StatusUpdateDao;

@Service
public class StatusUpdateService {

	@Autowired
	private StatusUpdateDao dao;

	public void save(StatusUpdate status) {
		dao.save(status);
	}

	public StatusUpdate getLatest() {
		return dao.findFirstByOrderByUpdatedDesc();
	}
}
