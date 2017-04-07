package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semester5.project.model.AppUser;
import semester5.project.model.Profile;
import semester5.project.model.ProfileDao;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;

	public void save(Profile profile) {
		profileDao.save(profile);
	}

	public Profile getUserProfile(AppUser user) {
		return profileDao.findByUser(user);
	}

}
