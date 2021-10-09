package co.com.api.filter;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import co.com.entity.entity.UserEntity;
import co.com.entity.response.JWTResponseDTO;
import co.com.irepository.IUserRepository;

import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
public class WebSecurityConfig extends org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter{

	@Autowired IUserRepository userRepository;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				UserEntity user = userRepository.byEmail(email);
				if( !Objects.isNull(user) ){
					JWTResponseDTO userResponse = new JWTResponseDTO(user.getEmail(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("STANDARD_USER, ADMIN_USER"));
					userResponse.setUser(user);
					userResponse.setId(user.getId());
					UserDetails userDetails = userResponse;
					return userDetails;
				}else {
					throw new UsernameNotFoundException(String.format("The username with email %s doesn't exist", email));
				}
			}
		};
	}

	@Bean
	@SuppressWarnings("deprecation")
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
