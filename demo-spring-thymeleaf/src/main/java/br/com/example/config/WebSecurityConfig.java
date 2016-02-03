package br.com.example.config;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import br.com.example.service.UsuarioRepositoryUserDetailsService;

@Configuration
@ComponentScan(basePackageClasses = UsuarioRepositoryUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private UserDetailsService userDetailsService;
	
	@Autowired private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll()
	            .antMatchers("/fonts/**").permitAll()
	            .antMatchers("/registrar/**").permitAll()
	            .antMatchers("/recuperar/**").permitAll()
	            .antMatchers("/isAuthenticated").permitAll()
	            .antMatchers("/contato/**").permitAll()
	            .antMatchers("/feedback/**").permitAll()
	            .antMatchers("/logout/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("/index")
	            .permitAll()
	            .and()
	        .logout()
	            .permitAll();
		http
			.rememberMe()
				.tokenRepository(persistentTokenRepository());
		http
			.csrf()
				.requireCsrfProtectionMatcher(new RequestMatcherImplementation());
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
				
	}
	
	@Bean
    public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
    }

	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	static List<RegexRequestMatcher> unprotectedMatchers;
	
	static {
		unprotectedMatchers = Arrays.asList(
				new RegexRequestMatcher("/registrar", null),
				new RegexRequestMatcher("/contato", null),
				new RegexRequestMatcher("/feedback", null),
				new RegexRequestMatcher("/logout", null)
				);
	}
	
	private final class RequestMatcherImplementation implements RequestMatcher {
		
		private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
		
		@Override
		public boolean matches(HttpServletRequest request) {
			if(allowedMethods.matcher(request.getMethod()).matches()){
	            return false;
	        }
			
			for (RegexRequestMatcher regexRequestMatcher : unprotectedMatchers) {
				if (regexRequestMatcher.matches(request)) {
					return false;
				}
			}
			return true;
		}
	}
	
}
