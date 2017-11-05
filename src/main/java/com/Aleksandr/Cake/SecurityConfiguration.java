package com.Aleksandr.Cake;

import static com.Aleksandr.utils.CONST.*;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	/**
	 * Line 21 → password encoder reference implemented in WebMvcConfig.java Line 24
	 * → data source implemented out of the box by Spring Boot. We only need to
	 * provide the database information in the application.properties file (please
	 * see the reference below).
	 * Lines 27 and 30 → Reference to user and role
	 * queries stored in application.properties file (please see the reference
	 * below).
	 * Lines from 33 to 41 → AuthenticationManagerBuilder provides a
	 * mechanism to get a user based on the password encoder, data source, user
	 * query and role query.
	 * Lines from 44 to 61 → Here we define the antMatchers to
	 * provide access based on the role(s) (lines 48 to 51), the parameters for the
	 * login process (lines 55 to 56), the success login page(line 53), the failure
	 * login page(line 53), and the logout page (line 58).
	 * Lines from 64 to 68 → Due
	 * we have implemented Spring Security we need to let Spring knows that our
	 * resources folder can be served skipping the antMatchers defined.
	 */
	private final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("Start class SecurityConfiguration!!!");
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("-- Start class SecurityConfiguration with role!!");
		http.authorizeRequests().antMatchers("/webjars/**").permitAll()
			.antMatchers(URL_GENERAL, URL_INDEX, URL_REGISTRATION, URL_PRODUCTS, "/calendar").permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
		    .antMatchers("/user/**").hasAuthority("USER").anyRequest().authenticated() //this method anyRequest().authenticated() must be after all roles
		.and().csrf().disable()
		// in class customSuccessHandler makes all mapping after authenticated. For simple request use  method (.defaultSuccessUrl("/admin/home"))
		.formLogin().loginPage(URL_INDEX).failureUrl(URL_INDEX+"?error=true").successHandler(customSuccessHandler)
			.usernameParameter("email").passwordParameter("password")
		.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher(URL_LOGOUT)).logoutSuccessUrl(URL_GENERAL)
			.invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.and()
			.exceptionHandling().accessDeniedPage(URL_ACCESS_DENIED);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
