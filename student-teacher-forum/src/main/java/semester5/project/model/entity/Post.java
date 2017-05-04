package semester5.project.model.entity;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.owasp.html.PolicyFactory;
import org.springframework.format.annotation.DateTimeFormat;

import semester5.project.model.dto.FileInfo;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 5, max = 20, message = "{addpost.text.size}")
	@Column(name = "title")
	private String title;

	@Size(min = 2, max = 255, message = "{addpost.text.size}")
	@Column(name = "text")
	private String text;

	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private Date updated;

	@Column(name = "photo_directory", length = 10)
	private String photoDirectory;

	@Column(name = "photo_name", length = 10)
	private String photoName;

	@Column(name = "photo_extention", length = 5)
	private String photoExtention;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "likes", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	@OrderColumn(name = "display_order")
	private Set<AppUser> likes;

	@PrePersist
	protected void onCreate() {
		if (updated == null)
			updated = new Date();
	}

	public Post() {

	}

	public Post(String text) {
		this.text = text;
	}

	public Post(String text, Date updated) {
		this.text = text;
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Set<AppUser> getLikes() {
		return likes;
	}

	public void setLikes(Set<AppUser> likes) {
		this.likes = likes;
	}

	public void addLike(AppUser user) {
		this.likes.add(user);
	}

	public void removeLike(AppUser user) {
		likes.remove(user);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

	// Create a post that is suitable for displaying via JSP
	public void safeCopyFrom(Post other) {
		if (other.text != null)
			this.text = other.text;
		if (other.title != null)
			this.title = other.title;

	}

	// Create a post that is suitable for saving
	public void safeMergeFrom(Post webPost, PolicyFactory htmlPolicy) {
		if (webPost.text != null)
			this.text = htmlPolicy.sanitize(webPost.text);
		if (webPost.title != null)
			this.title = htmlPolicy.sanitize(webPost.title);

	}
}
