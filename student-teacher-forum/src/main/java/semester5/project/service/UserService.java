package semester5.project.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import semester5.project.model.AppUser;
import semester5.project.model.TokenType;
import semester5.project.model.UserDao;
import semester5.project.model.VerificationDao;
import semester5.project.model.VerificationToken;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private VerificationDao verificationDao;

	public void register(AppUser user) {
		user.setRole("ROLE_USER");
		userDao.save(user);
	}

	public void save(AppUser user) {
		userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		AppUser user = userDao.findByEmail(email);
		if (user == null) {
			return null;
		}
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		String password = user.getPassword();
		Boolean enabled = user.getEnabled();
		return new User(email, password, enabled, true, true, true, auth);
	}

	public String createEmailVerificationToken(AppUser user) {
		VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTRATION);
		verificationDao.save(token);
		return token.getToken();
	}

	public VerificationToken getVerificationToken(String token) {
		return verificationDao.findByToken(token);
	}

	public void deleteToken(VerificationToken token) {
		verificationDao.delete(token);

	}
}
