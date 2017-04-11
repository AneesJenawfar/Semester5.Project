package semester5.project.controllers;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.exception.ImageTooSmallException;
import semester5.project.exception.InvalidFileException;
import semester5.project.model.AppUser;
import semester5.project.model.FileInfo;
import semester5.project.model.Profile;
import semester5.project.service.FileService;
import semester5.project.service.ProfileService;
import semester5.project.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PolicyFactory htmlPolicy;

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	@RequestMapping(value = "/profile")
	public ModelAndView showProfile(ModelAndView mav) {

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		mav.getModel().put("profile", webProfile);
		mav.setViewName("app.profile");
		return mav;
	}

	@RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
	public ModelAndView editProfile(ModelAndView mav) {

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		mav.getModel().put("profile", webProfile);
		mav.setViewName("app.editProfile");
		return mav;
	}

	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
	public ModelAndView editProfile(ModelAndView mav, @Valid Profile webProfile, BindingResult result) {

		mav.setViewName("app.editProfile");

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		profile.safeMergeFrom(webProfile, htmlPolicy);

		if (!result.hasErrors()) {
			profileService.save(profile);
			mav.setViewName("redirect:/profile");
		}
		return mav;
	}

	@RequestMapping(value = "/upload-photo", method = RequestMethod.POST)
	public ModelAndView handlePhotoUpload(ModelAndView mav, @RequestParam("file") MultipartFile file) {
		mav.setViewName("redirect:/profile");

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		Path oldPhotoPath = profile.getPhoto(photoUploadDirectory);

		try {
			FileInfo photoInfo = fileService.saveImageFile(file, photoUploadDirectory, "Photos", "P" + user.getId(),
					100, 100);
			profile.setPhotoDetails(photoInfo);
			profileService.save(profile);

			if (oldPhotoPath != null)
				Files.delete(oldPhotoPath);

		} catch (InvalidFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageTooSmallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/profile-photo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> servePhoto() throws IOException {
		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		Path photoPath = Paths.get(photoUploadDirectory, "Default", "Default.jpg");

		if (profile != null && profile.getPhoto(photoUploadDirectory) != null)
			photoPath = profile.getPhoto(photoUploadDirectory);

		return ResponseEntity.ok().contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}
}
