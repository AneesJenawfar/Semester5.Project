package semester5.project.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import semester5.project.App;
import semester5.project.service.FileService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration

@Transactional
public class FileServiceTest {

	@Autowired
	private FileService fileService;

	@Value("${profile.photo.directory}")
	private String photoUploadDirectory;

	@Test
	public void testGetExtention() throws Exception {
		Method method = FileService.class.getDeclaredMethod("getFileExtentions", String.class);
		method.setAccessible(true);
		assertEquals("Should be png", "png", (String) method.invoke(fileService, "test.png"));
		assertEquals("Should be doc", "doc", (String) method.invoke(fileService, "p.doc"));
		assertEquals("Should be jpeg", "jpeg", (String) method.invoke(fileService, "tfile.jpeg"));
		assertNull("Should be png", (String) method.invoke(fileService, "xyz"));

	}

	@Test
	public void testIsImageExtention() throws Exception {
		Method method = FileService.class.getDeclaredMethod("isImageExtention", String.class);
		method.setAccessible(true);
		assertTrue("png should be Valid", (Boolean) method.invoke(fileService, "png"));
		assertTrue("png should be Valid", (Boolean) method.invoke(fileService, "PNG"));
		assertTrue("png should be Valid", (Boolean) method.invoke(fileService, "Jpeg"));
		assertTrue("png should be Valid", (Boolean) method.invoke(fileService, "jpg"));
		assertTrue("png should be Valid", (Boolean) method.invoke(fileService, "gif"));
		assertFalse("png should be inValid", (Boolean) method.invoke(fileService, "doc"));
		assertFalse("png should be invalid", (Boolean) method.invoke(fileService, "gi"));

	}

	@Test
	public void testMakeSubdirectory() throws Exception {
		Method method = FileService.class.getDeclaredMethod("makeSubdirectory", String.class, String.class);
		method.setAccessible(true);

		for (int i = 0; i < 1000; i++) {
			File created = (File) method.invoke(fileService, photoUploadDirectory, "photo");
			assertTrue("Directory Should Exist" + created.getAbsolutePath(), created.exists());
		}

	}
}
