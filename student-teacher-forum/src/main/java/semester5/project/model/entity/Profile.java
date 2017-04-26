package semester5.project.model.entity;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.owasp.html.PolicyFactory;

import semester5.project.model.dto.FileInfo;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@Column(name = "about", length = 5000)
	@Size(max = 5000, message = "{about.size}")
	private String about;

	@Column(name = "photo_directory", length = 10)
	private String photoDirectory;

	@Column(name = "photo_name", length = 10)
	private String photoName;

	@Column(name = "photo_extention", length = 5)
	private String photoExtention;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "profile_interests", joinColumns = { @JoinColumn(name = "profier_id") }, inverseJoinColumns = {
			@JoinColumn(name = "interest_id") })
	@OrderColumn(name = "display_order")
	private Set<Interest> interests;

	public Profile() {

	}

	public Profile(AppUser user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	// Create a profile that is suitable for displaying via JSP
	public void safeCopyFrom(Profile other) {
		if (other.about != null)
			this.about = other.about;
		if (other.interests != null)
			this.interests = other.interests;
	}

	// Create a profile that is suitable for saving
	public void safeMergeFrom(Profile webProfile, PolicyFactory htmlPolicy) {
		if (webProfile.about != null)
			this.about = htmlPolicy.sanitize(webProfile.about);

	}

	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtention() {
		return photoExtention;
	}

	public void setPhotoExtention(String photoExtention) {
		this.photoExtention = photoExtention;
	}

	public void setPhotoDetails(FileInfo info) {
		this.photoDirectory = info.getSubDirectory();
		this.photoExtention = info.getExtention();
		this.photoName = info.getBasename();
	}

	public Path getPhoto(String baseDirectory) {
		if (photoName == null)
			return null;
		return Paths.get(baseDirectory, photoDirectory, photoName + "." + photoExtention);
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	public void addInterest(Interest interest) {
		this.interests.add(interest);

	}

	public void removeInterest(String interestName) {
		interests.remove(new Interest(interestName));

	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", user=" + user + ", about=" + about + ", photoDirectory=" + photoDirectory
				+ ", photoName=" + photoName + ", photoExtention=" + photoExtention + ", interests=" + interests + "]";
	}

}
