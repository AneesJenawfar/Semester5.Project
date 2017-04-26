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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import semester5.project.exception.ImageTooSmallException;
import semester5.project.exception.InvalidFileException;
import semester5.project.model.dto.FileInfo;
import semester5.project.model.entity.AppUser;
import semester5.project.model.entity.Interest;
import semester5.project.model.entity.Profile;
import semester5.project.service.FileService;
import semester5.project.service.InterestService;
import semester5.project.service.ProfileService;
import semester5.project.service.UserService;
import semester5.project.status.PhotoUploadStatus;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FileService fileService;

	@Autowired
	private InterestService interestService;

	@Autowired
	private PolicyFactory htmlPolicy;

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;

	@Value("${photo.upload.ok}")
	private String photoUploaded;

	@Value("${photo.upload.invalid}")
	private String photoInvalid;

	@Value("${photo.upload.iofailed}")
	private String photoIOFailed;

	@Value("${photo.upload.small}")
	private String photoSmall;

	private AppUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.get(email);
	}

	private ModelAndView showProfile(AppUser user) {
		ModelAndView mav = new ModelAndView();
		if (user == null) {
			mav.setViewName("redirect:/");
			return mav;
		}
		Profile profile = profileService.getUserProfile(user);
		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile webProfile = new Profile();
		webProfile.safeCopyFrom(profile);
		mav.getModel().put("userId", user.getId());
		mav.getModel().put("profile", webProfile);
		mav.setViewName("app.profile");
		return mav;
	}

	@RequestMapping(value = "/profile")
	public ModelAndView showProfile() {

		AppUser user = getUser();
		ModelAndView mav = showProfile(user);
		mav.getModel().put("owner", true);
		return mav;
	}

	@RequestMapping(value = "/profile/{id}")
	public ModelAndView showProfile(@PathVariable("id") Long id) {

		AppUser user = userService.get(id);

		ModelAndView mav = showProfile(user);
		mav.getModel().put("owner", false);
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
	@ResponseBody // Return Data JSON format
	public ResponseEntity<PhotoUploadStatus> handlePhotoUpload(@RequestParam("file") MultipartFile file) {

		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);

		Path oldPhotoPath = profile.getPhoto(photoUploadDirectory);

		PhotoUploadStatus status = new PhotoUploadStatus(photoUploaded);

		try {
			FileInfo photoInfo = fileService.saveImageFile(file, photoUploadDirectory, "Photos", "P" + user.getId(),
					100, 100);
			profile.setPhotoDetails(photoInfo);
			profileService.save(profile);

			if (oldPhotoPath != null)
				Files.delete(oldPhotoPath);

		} catch (InvalidFileException e) {
			status.setMessage(photoInvalid);
			e.printStackTrace();
		} catch (IOException e) {
			status.setMessage(photoIOFailed);
			e.printStackTrace();
		} catch (ImageTooSmallException e) {
			status.setMessage(photoSmall);
			e.printStackTrace();
		}

		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@RequestMapping(value = "/profile-photo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> servePhoto(@PathVariable("id") Long id) throws IOException {
		AppUser user = userService.get(id);
		Profile profile = profileService.getUserProfile(user);

		Path photoPath = Paths.get(photoUploadDirectory, "Default", "Default.jpg");

		if (profile != null && profile.getPhoto(photoUploadDirectory) != null)
			photoPath = profile.getPhoto(photoUploadDirectory);

		return ResponseEntity.ok().contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}

	@RequestMapping(value = "/save-interest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveInterest(@RequestParam("name") String interestName) {
		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		String cleanedInterestName = htmlPolicy.sanitize(interestName);

		Interest interest = interestService.createIfNotExist(cleanedInterestName);

		profile.addInterest(interest);
		profileService.save(profile);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete-interest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteInterest(@RequestParam("name") String interestName) {
		AppUser user = getUser();
		Profile profile = profileService.getUserProfile(user);
		profile.removeInterest(interestName);
		profileService.save(profile);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
