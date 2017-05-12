package semester5.project.model.entity;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import semester5.project.model.dto.FileInfo;

@Entity
@Table(name = "post_photo")
public class PostPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne(targetEntity = Post.class)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(name = "photo_directory", length = 10)
	private String photoDirectory;

	@Column(name = "photo_name", length = 10)
	private String photoName;

	@Column(name = "photo_extention", length = 5)
	private String photoExtention;

	public PostPhoto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
	public String toString() {
		return "PostPhoto [id=" + id + ", post=" + post + ", photoDirectory=" + photoDirectory + ", photoName="
				+ photoName + ", photoExtention=" + photoExtention + "]";
	}

}
