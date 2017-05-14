package semester5.project.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "post_notification")
public class PostNotification {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "attacker_id", nullable = false)
	private AppUser attacker;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "target_id", nullable = false)
	private AppUser targetUser;

	@ManyToOne(targetEntity = Post.class)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private Date time;

	@Column(name = "action")
	private String action;

	@PrePersist
	protected void onCreate() {
		if (time == null)
			time = new Date();
	}

	public PostNotification() {

	}

	public PostNotification(AppUser attacker, AppUser targetUser, Post post, String action) {
		this.attacker = attacker;
		this.targetUser = targetUser;
		this.post = post;
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getAttacker() {
		return attacker;
	}

	public void setAttacker(AppUser attacker) {
		this.attacker = attacker;
	}

	public AppUser getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(AppUser targetUser) {
		this.targetUser = targetUser;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "PostNotification [id=" + id + ", attacker=" + attacker.getEmail() + ", targetUser="
				+ targetUser.getEmail() + ", post=" + post.getTitle() + ", time=" + time + ", action=" + action + "]";
	}

}
