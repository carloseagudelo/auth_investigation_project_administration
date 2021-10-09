package co.com.iservices;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;

import co.com.entity.request.UserRequestDTO;

public interface IUserService {
	
	public ResponseEntity<String> singup(UserRequestDTO user) throws ConstraintViolationException, Exception;
	
	public ResponseEntity<String> getAll() throws Exception;
	
	public ResponseEntity<String> validateToken(String token) throws Exception;

}
