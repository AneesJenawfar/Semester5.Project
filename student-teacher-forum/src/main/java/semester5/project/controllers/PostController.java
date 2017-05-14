package semester5.project.controllers;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import semester5.project.model.entity.Post;
import semester5.project.model.entity.PostPhoto;
import semester5.project.service.FileService;
import semester5.project.service.PostPhotoService;
import semester5.project.service.PostService;
import semester5.project.service.UserService;
import semester5.project.status.ActionStatus;

@Controller
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PostPhotoService postPhotoService;

	@Autowired
	private PolicyFactory htmlPolicy;

	@Value("${post.photo.directory}")
	private String postPhotoDirectory;

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

	@RequestMapping(value = "/addpost", method = RequestMethod.GET)
	ModelAndView addPost(ModelAndView mav, @ModelAttribute("post") Post post) {
		mav.setViewName("app.addPost");
		return mav;
	}

	@RequestMapping(value = "/addpost", method = RequestMethod.POST)
	ModelAndView addPost(ModelAndView mav, @Valid Post post, BindingResult result) {
		AppUser user = getUser();
		post.setUser(user);
		mav.setViewName("app.addPost");
		if (!result.hasErrors()) {
			postService.save(post);
			mav.getModel().put("post", post);
			mav.setViewName("app.addPhoto");
		}
		return mav;
	}

	@RequestMapping(value = "/viewpost", method = RequestMethod.GET)
	public ModelAndView viewPost(ModelAndView mav, @RequestParam(name = "p", defaultValue = "1") int pagenumber) {

		AppUser user = getUser();
		Page<Post> page = postService.getPage(pagenumber);
		mav.getModel().put("user", user);
		mav.getModel().put("page", page);
		mav.setViewName("app.viewPost");
		return mav;
	}

	@RequestMapping(value = "/deletepost", method = RequestMethod.GET)
	public ModelAndView deletePost(ModelAndView mav, @RequestParam(name = "id") Long id) {
		Post post = postService.get(id);
		PostPhoto postPhoto = postPhotoService.getPostPhoto(post);
		postPhotoService.delete(postPhoto.getId());
		postService.delete(id);
		mav.setViewName("redirect:/viewpost");
		return mav;
	}

	@RequestMapping(value = "/editpost", method = RequestMethod.GET)
	public ModelAndView editPost(ModelAndView mav, @RequestParam(name = "id") Long id) {
		Post post = postService.get(id);

		Post webPost = new Post();
		webPost.safeCopyFrom(post);

		mav.getModel().put("realpost", post);
		mav.getModel().put("post", webPost);
		mav.setViewName("app.editPost");
		return mav;
	}

	@RequestMapping(value = "/editpost", method = RequestMethod.POST)
	public ModelAndView editPost(ModelAndView mav, @Valid Post webPost, @Valid Long id, BindingResult result) {

		mav.setViewName("app.editPost");

		Post post = postService.get(id);
		post.safeMergeFrom(webPost, htmlPolicy);

		if (!result.hasErrors()) {
			postService.save(post);
			mav.setViewName("redirect:/viewpost");
		}
		return mav;
	}

	@RequestMapping(value = "/upload-post-photo/{id}", method = RequestMethod.POST)
	@ResponseBody // Return Data JSON format
	public ResponseEntity<ActionStatus> handlePhotoUpload(@RequestParam("file") MultipartFile file,
			@PathVariable("id") Long id) {

		Post post = postService.get(id);
		post.setHasPhoto(true);
		PostPhoto postPhoto = postPhotoService.getPostPhoto(post);
		if (postPhoto == null)
			postPhoto = new PostPhoto();
		postPhoto.setPost(post);
		Path oldPhotoPath = postPhoto.getPhoto(postPhotoDirectory);

		ActionStatus status = new ActionStatus(photoUploaded);

		try {
			FileInfo photoInfo = fileService.saveImageFile(file, postPhotoDirectory, "Photos", "P" + post.getId(), 400,
					400);
			postPhoto.setPhotoDetails(photoInfo);
			postPhotoService.save(postPhoto);

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

	@RequestMapping(value = "/post-photo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> servePhoto(@PathVariable("id") Long id) throws IOException {
		Post post = postService.get(id);
		PostPhoto postPhoto = postPhotoService.getPostPhoto(post);

		Path photoPath = null;

		if (postPhoto != null && postPhoto.getPhoto(postPhotoDirectory) != null)
			photoPath = postPhoto.getPhoto(postPhotoDirectory);

		return ResponseEntity.ok().contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
	}
}
