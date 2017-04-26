package semester5.project.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
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
public class BulkTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private InterestService interestService;

	private static final String nameFile = "/semester5/project/data/names.txt";
	private static final String interestsFile = "/semester5/project/data/hobbies.txt";

	private List<String> loadFile(String filename, int maxLength) throws IOException {
		Path filePath = new ClassPathResource(filename).getFile().toPath();

		Stream<String> stream = Files.lines(filePath);

		// @formatter:off
		List<String> items = stream
			.filter(line -> !line.isEmpty())
			.map(line -> line.trim())
			.filter(line -> line.length() <= maxLength)
			.map(line -> line.substring(0, 1).toUpperCase() + line.substring(1).toLowerCase())
			.collect(Collectors.toList());
		
		// @formatter:on

		stream.close();
		return items;
	}

	// @Ignore
	@Test
	public void createTestData() throws IOException {
		Random random = new Random();
		List<String> names = loadFile(nameFile, 25);
		List<String> interests = loadFile(interestsFile, 25);

		for (int i = 0; i < 200; i++) {
			String firstname = names.get(random.nextInt(names.size()));
			String surname = names.get(random.nextInt(names.size()));

			String email = firstname.toLowerCase() + surname.toLowerCase() + "@example.com";

			if (userService.get(email) != null) {
				continue;
			}

			String password = "pass" + firstname.toLowerCase();
			password = password.substring(0, Math.min(15, password.length()));

			assertTrue(password.length() <= 15);

			AppUser user = new AppUser(email, password, firstname, surname);
			user.setEnabled(random.nextInt(5) != 0);

			userService.register(user);

			Profile profile = new Profile(user);
			int numberInterest = random.nextInt(7);
			Set<Interest> userInterests = new HashSet<Interest>();

			for (int j = 0; j < numberInterest; j++) {
				String interestText = interests.get(random.nextInt(interests.size()));

				Interest interest = interestService.createIfNotExist(interestText);
				userInterests.add(interest);
			}

			profile.setInterests(userInterests);
			profileService.save(profile);
		}
		assertTrue(true);
	}
}
