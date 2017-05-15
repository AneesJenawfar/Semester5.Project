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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "chat")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "user_1", nullable = false)
	private AppUser user1;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "user_2", nullable = false)
	private AppUser user2;

	@Size(min = 1, max = 100, message = "{chat.text.size}")
	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "owner")
	private Boolean owner = false;

	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private Date time;

	@PrePersist
	protected void onCreate() {
		if (time == null)
			time = new Date();
	}

	public Chat() {

	}

	public Chat(AppUser user1, AppUser user2, String text, Boolean owner) {
		this.user1 = user1;
		this.user2 = user2;
		this.text = text;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getUser1() {
		return user1;
	}

	public void setUser1(AppUser user1) {
		this.user1 = user1;
	}

	public AppUser getUser2() {
		return user2;
	}

	public void setUser2(AppUser user2) {
		this.user2 = user2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getOwner() {
		return owner;
	}

	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", user1=" + user1.getEmail() + ", user2=" + user2.getEmail() + ", text=" + text
				+ ", owner=" + owner + ", time=" + time + "]";
	}

}
