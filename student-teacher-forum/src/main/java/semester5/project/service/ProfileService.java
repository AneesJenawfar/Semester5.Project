package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Profile;
import semester5.project.model.repository.ProfileDao;

@Service
public class ProfileService {

	@Value("${profile.pagesize}")
	private int pageSize;

	@Autowired
	private ProfileDao profileDao;

	public void save(Profile profile) {
		profileDao.save(profile);
	}

	public Profile getUserProfile(AppUser user) {
		return profileDao.findByUser(user);
	}

	public Page<Profile> getPage(int pagenumber) {
		PageRequest request = new PageRequest(pagenumber - 1, pageSize);
		return profileDao.findAll(request);
	}
}
