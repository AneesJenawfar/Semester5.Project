package semester5.project.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend")
public class Friend {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "sender_id", nullable = false)
	private AppUser sender;

	@ManyToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "accepter_id", nullable = false)
	private AppUser accepter;

	@Column(name = "confirmation")
	private Boolean confirm = false;

	public Friend() {
	}

	public Friend(AppUser sender, AppUser accepter) {
		this.sender = sender;
		this.accepter = accepter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getSender() {
		return sender;
	}

	public void setSender(AppUser sender) {
		this.sender = sender;
	}

	public AppUser getAccepter() {
		return accepter;
	}

	public void setAccepter(AppUser accepter) {
		this.accepter = accepter;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", sender=" + sender.getEmail() + ", accepter=" + accepter.getEmail() + ", confirm="
				+ confirm + "]";
	}

}
