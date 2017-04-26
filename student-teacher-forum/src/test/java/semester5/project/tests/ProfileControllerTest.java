package semester5.project.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class ProfileControllerTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private InterestService interestService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@WithMockUser(username = "anees@gmail.com")
	public void testSaveAndDeleteInterest() throws Exception {
		String interestName = "some interest_here";
		mockMvc.perform(post("/save-interest").param("name", interestName)).andExpect(status().isOk());

		Interest interest = interestService.get(interestName);

		assertNotNull("Interest should exist", interest);
		assertEquals("retreived interest text should match", interestName, interest.getName());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		AppUser user = userService.get(email);

		Profile profile = profileService.getUserProfile(user);

		assertTrue("Profile should contain interest", profile.getInterests().contains(new Interest(interestName)));

		mockMvc.perform(post("/delete-interest").param("name", interestName)).andExpect(status().isOk());

		profile = profileService.getUserProfile(user);
		assertFalse("Profile should not contain interest", profile.getInterests().contains(new Interest(interestName)));
	}
}
