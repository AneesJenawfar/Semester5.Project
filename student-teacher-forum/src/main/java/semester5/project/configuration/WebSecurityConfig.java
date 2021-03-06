package semester5.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import semester5.project.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http
			.authorizeRequests()
				.antMatchers("/","/about","/register","/confirmed","/invaliduser","/expiredtoken","/verifyemail","/search",
							"/profile-photo/*","/search-surname","/search-interest","/notify").permitAll()
				.antMatchers("/js/*","/css/*","/img/*").permitAll()
				.antMatchers("/addpost","/editpost","/deletepost","/viewpost","/profile","/edit-profile","/upload-photo",
							"/profile/*","/save-interest","/delete-interest","/comment", "/like", "/dislike",
							"/upload-post-photo/*", "/post-photo/{id}","/friend-status/*","/unfriend",
							"/accept-request","/send-request","/share","/notifications","/chats","/saveChat",
							"/get-chats","/new-chats","/chat-list","/all").authenticated()
				.anyRequest().denyAll()
				/*.antMatchers("").hasRole("ADMIN")*/
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/viewpost")
				.permitAll()
				.and()
			.logout()
				.permitAll();
		//@formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//@formatter:off
		auth
			.inMemoryAuthentication()
			.withUser("Anees")
			.password("anees")
			.roles("USER");
		//@formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

}
