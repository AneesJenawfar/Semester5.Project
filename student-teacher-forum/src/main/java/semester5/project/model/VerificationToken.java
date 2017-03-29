package semester5.project.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "verification")
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "token")
	private String token;

	@OneToOne(targetEntity = AppUser.class)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@Column(name = "expiry")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;

	@Column(name = "token_type")
	@Enumerated(EnumType.STRING)
	private TokenType type;

	@PrePersist
	private void setDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 24);
		expiry = c.getTime();
	}

	public VerificationToken() {

	}

	public VerificationToken(String token, AppUser user, TokenType type) {
		this.token = token;
		this.user = user;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

}
