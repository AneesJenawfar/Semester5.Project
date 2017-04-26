package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.Interest;
import semester5.project.model.repository.InterestDao;

@Service
public class InterestService {

	@Autowired
	private InterestDao interestdao;

	public long count() {
		return interestdao.count();
	}

	public Interest get(String name) {
		return interestdao.findOneByName(name);
	}

	public void save(Interest interest) {
		interestdao.save(interest);
	}

	public Interest createIfNotExist(String interestName) {
		Interest interest = interestdao.findOneByName(interestName);
		if (interest == null) {
			interest = new Interest(interestName);
			interestdao.save(interest);
		}
		return interest;
	}
}
