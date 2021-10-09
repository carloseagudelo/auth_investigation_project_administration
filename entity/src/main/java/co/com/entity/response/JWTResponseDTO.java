package co.com.entity.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import co.com.entity.entity.UserEntity;

public class JWTResponseDTO extends User {
	
	private static final long serialVersionUID = 6674077300313869696L;
	
	public JWTResponseDTO(String expositorname, String password, Collection<? extends GrantedAuthority> authorities) {
		super(expositorname, password, authorities);
	}
	
	private UserEntity user;
	
	private String id;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}

}
