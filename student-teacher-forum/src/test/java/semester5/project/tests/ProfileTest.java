package semester5.project.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import semester5.project.App;
import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Interest;
import semester5.project.model.entity.Profile;
import semester5.project.service.InterestService;
import semester5.project.service.ProfileService;
import semester5.project.service.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration

@Transactional
public class ProfileTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private InterestService interestService;

	private AppUser[] users = { new AppUser("check1@gmail.com", "12345678", "check", "01"),
			new AppUser("check2@gmail.com", "qessedx", "check", "02"),
			new AppUser("check3@gmail.com", "fugsdfsbf", "check", "03") };

	private String[][] interests = { { "maths", "science", "history" }, { "maths", "maths", "health" },
			{ "health", "language" } };

	@Test
	public void testInterests() {
		for (int i = 0; i < users.length; i++) {
			AppUser user = users[i];
			String[] interestArray = interests[i];
			userService.save(user);
			HashSet<Interest> interestSet = new HashSet<>();

			for (String interestName : interestArray) {
				Interest interest = interestService.createIfNotExist(interestName);
				interestSet.add(interest);
				assertNotNull("Interest should not be null", interest);
				assertNotNull("Interest should have ID", interest.getId());
				assertEquals("Name should match", interestName, interest.getName());
			}

			Profile profile = new Profile(user);
			profile.setInterests(interestSet);
			profileService.save(profile);

			Profile retrieved = profileService.getUserProfile(user);

			assertEquals("Interest set should match", interestSet, retrieved.getInterests());
		}
	}
}
