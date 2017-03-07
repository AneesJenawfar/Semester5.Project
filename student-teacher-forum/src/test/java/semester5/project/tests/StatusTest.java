package semester5.project.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import semester5.project.App;
import semester5.project.model.StatusUpdate;
import semester5.project.model.StatusUpdateDao;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration
@Transactional
public class StatusTest {

	@Autowired
	private StatusUpdateDao statusupdatedao;

	@Test
	public void TestSave() {
		StatusUpdate status = new StatusUpdate("This is a Test Status Update");
		statusupdatedao.save(status);

		assertNotNull("Non-null ID", status.getId());
		assertNotNull("Non-null date", status.getUpdated());

		StatusUpdate retrieved = statusupdatedao.findOne(status.getId());
		assertEquals("Matching StatusUpdate", status, retrieved);
	}
}
